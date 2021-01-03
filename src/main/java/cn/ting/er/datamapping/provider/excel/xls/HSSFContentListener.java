package cn.ting.er.datamapping.provider.excel.xls;

import cn.ting.er.datamapping.Setting;
import cn.ting.er.datamapping.exception.MappingException;
import cn.ting.er.datamapping.listener.ParseListener;
import org.apache.poi.hssf.eventusermodel.*;
import org.apache.poi.hssf.eventusermodel.dummyrecord.LastCellOfRowDummyRecord;
import org.apache.poi.hssf.eventusermodel.dummyrecord.MissingCellDummyRecord;
import org.apache.poi.hssf.model.HSSFFormulaParser;
import org.apache.poi.hssf.record.*;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

/**
 * @author wenting.Li
 * @version 0.0.1
 * @since JDK 8
 */
public class HSSFContentListener implements HSSFListener {
    private ParseListener listener;
    private boolean sheetMatch = false;
    private List<String> currentRow;

    /** Should we output the formula, or the value it has? */
    private boolean outputFormulaValues = true;

    /** For parsing Formulas */
    private EventWorkbookBuilder.SheetRecordCollectingListener workbookBuildingListener;
    private HSSFWorkbook stubWorkbook;

    // Records we pick up as we process
    private SSTRecord sstRecord;
    private FormatTrackingHSSFListener formatListener;

    /** So we known which sheet we're on */
    private int sheetIndex = -1;
    private BoundSheetRecord[] orderedBSRs;
    private List<BoundSheetRecord> boundSheetRecords = new ArrayList<>();

    // For handling formulas with string results
    private int nextRow;
    private int nextColumn;
    private boolean outputNextStringRecord;
    private Setting setting;

    public HSSFContentListener(ParseListener listener, Setting setting) {
        this.listener = listener;
        this.setting = setting;
    }

    /**
     * Main HSSFListener method, processes events, and outputs the
     *  CSV as the file is processed.
     */
    @Override
    public void processRecord(org.apache.poi.hssf.record.Record record) {
        int rowIndex = -1;
        int columnIndex = -1;
        String cellValue = null;

        switch (record.getSid())
        {
            case BoundSheetRecord.sid:
                boundSheetRecords.add((BoundSheetRecord)record);
                break;
            case BOFRecord.sid:
                BOFRecord br = (BOFRecord)record;
                if(br.getType() == BOFRecord.TYPE_WORKSHEET) {
                    // Create sub workbook if required
                    if(workbookBuildingListener != null && stubWorkbook == null) {
                        stubWorkbook = workbookBuildingListener.getStubHSSFWorkbook();
                    }

                    // Output the worksheet name
                    // Works by ordering the BSRs by the location of
                    //  their BOFRecords, and then knowing that we
                    //  process BOFRecords in byte offset order
                    sheetIndex++;
                    if(orderedBSRs == null) {
                        orderedBSRs = BoundSheetRecord.orderByBofPosition(boundSheetRecords);
                    }
                    String sheetName=orderedBSRs[sheetIndex].getSheetname();
                    startSheet(sheetName, sheetIndex);
                }
                break;

            case SSTRecord.sid:
                sstRecord = (SSTRecord) record;
                break;

            case BlankRecord.sid:
                BlankRecord brec = (BlankRecord) record;

                rowIndex = brec.getRow();
                columnIndex = brec.getColumn();
                cellValue = "";
                break;
            case BoolErrRecord.sid:
                BoolErrRecord berec = (BoolErrRecord) record;

                rowIndex = berec.getRow();
                columnIndex = berec.getColumn();
                cellValue = "";
                break;

            case FormulaRecord.sid:
                FormulaRecord frec = (FormulaRecord) record;

                rowIndex = frec.getRow();
                columnIndex = frec.getColumn();

                if(outputFormulaValues) {
                    if(Double.isNaN( frec.getValue() )) {
                        // Formula result is a string
                        // This is stored in the next record
                        outputNextStringRecord = true;
                        nextRow = frec.getRow();
                        nextColumn = frec.getColumn();
                    } else {
                        cellValue = formatListener.formatNumberDateCell(frec);
                    }
                } else {
                    cellValue = '"' +
                            HSSFFormulaParser.toFormulaString(stubWorkbook, frec.getParsedExpression()) + '"';
                }
                break;
            case StringRecord.sid:
                if(outputNextStringRecord) {
                    // String for formula
                    StringRecord srec = (StringRecord)record;
                    cellValue = srec.getString();
                    rowIndex = nextRow;
                    columnIndex = nextColumn;
                    outputNextStringRecord = false;
                }
                break;

            case LabelRecord.sid:
                LabelRecord lrec = (LabelRecord) record;

                rowIndex = lrec.getRow();
                columnIndex = lrec.getColumn();
                cellValue = '"' + lrec.getValue() + '"';
                break;
            case LabelSSTRecord.sid:
                LabelSSTRecord lsrec = (LabelSSTRecord) record;

                rowIndex = lsrec.getRow();
                if(sstRecord == null) {
                    cellValue = handleInvalidCell("(No SST Record, can't identify string)");
                } else {
                    cellValue = '"' + sstRecord.getString(lsrec.getSSTIndex()).toString() + '"';
                }
                break;
            case NoteRecord.sid:
                NoteRecord nrec = (NoteRecord) record;

                rowIndex = nrec.getRow();
                columnIndex = nrec.getColumn();
                break;
            case NumberRecord.sid:
                NumberRecord numrec = (NumberRecord) record;

                rowIndex = numrec.getRow();
                columnIndex = numrec.getColumn();

                // Formatter
                cellValue = formatListener.formatNumberDateCell(numrec);
                break;
            case RKRecord.sid:
                RKRecord rkrec = (RKRecord) record;

                rowIndex = rkrec.getRow();
                columnIndex = rkrec.getColumn();
                cellValue = handleInvalidCell("(TODO)");
                break;
            default:
                break;
        }

        if (skipSheet()) {
            return;
        }
        // Handle missing column
        if(record instanceof MissingCellDummyRecord) {
            MissingCellDummyRecord mc = (MissingCellDummyRecord)record;
            rowIndex = mc.getRow();
            columnIndex = mc.getColumn();
            cellValue = "";
        }

        tryStartRow(rowIndex);

        if (cellValue != null) {
            this.currentRow.add(cellValue);
        }

        // Handle end of row
        if(record instanceof LastCellOfRowDummyRecord) {
          endRow();
        }
    }

    private String handleInvalidCell(String tip) {
        if (setting.isIgnoreInvalidCell()) {
            throw new MappingException(tip);
        }
        return null;
    }


    private void startSheet(String sheetName, int sheetIndex) {
        this.sheetMatch = listener.start(sheetName, sheetIndex);
    }
    private void tryStartRow(int rowIndex) {
        if (rowIndex != -1 && this.currentRow == null) {
            this.currentRow = new ArrayList<>();
        }
    }

    private void endRow() {
        if (this.currentRow == null) {
            return;
        }
        listener.row(this.currentRow, this.nextColumn);
    }

    private boolean skipSheet() {
        return !this.sheetMatch;
    }

    public boolean isOutputFormulaValues() {
        return outputFormulaValues;
    }

    public void setOutputFormulaValues(boolean outputFormulaValues) {
        this.outputFormulaValues = outputFormulaValues;
    }

    public EventWorkbookBuilder.SheetRecordCollectingListener getWorkbookBuildingListener() {
        return workbookBuildingListener;
    }

    public void setWorkbookBuildingListener(EventWorkbookBuilder.SheetRecordCollectingListener workbookBuildingListener) {
        this.workbookBuildingListener = workbookBuildingListener;
    }

    public FormatTrackingHSSFListener getFormatListener() {
        return formatListener;
    }

    public void setFormatListener(FormatTrackingHSSFListener formatListener) {
        this.formatListener = formatListener;
    }
}
