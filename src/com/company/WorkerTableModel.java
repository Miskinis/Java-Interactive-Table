package com.company;

import javax.swing.table.AbstractTableModel;
import java.util.ArrayList;
import java.util.Vector;

public class WorkerTableModel extends AbstractTableModel
{
    public ArrayList<Worker> currentTableData;

    //Needed for value filtering
    private ArrayList<Worker> originalTableData;

    private static final String[] columnNames = new String[]{"Name", "Last Name", "Work Place", "Job", "Earnings"};
    private static final Class[] columnClasses = {String.class, String.class, String.class, String.class, Float.class};

    public WorkerTableModel()
    {
        currentTableData = new ArrayList<>();
    }

    public void setCurrentTableData(ArrayList<Worker> newData)
    {
        currentTableData = newData;
        originalTableData = new ArrayList<>(currentTableData);
        fireTableDataChanged();
    }

    public void FilterDataByEarnings(Object filterValue)
    {
        currentTableData = new ArrayList<>(originalTableData);

        if (filterValue.toString().equals(""))
        {
            fireTableDataChanged();
            return;
        }

        float parsedValue = Float.valueOf(filterValue.toString());
        ArrayList<Worker> filteredWorkers = new ArrayList<>();
        for (int i = 0; i < currentTableData.size(); i++)
        {
            Worker worker = currentTableData.get(i);
            if (worker.earnings > parsedValue)
            {
                filteredWorkers.add(worker);
            }
        }
        currentTableData = filteredWorkers;
        fireTableDataChanged();

    }

    public void addEmptyRow()
    {
        currentTableData.add(new Worker());
        if(originalTableData == null)
        {
            originalTableData = new ArrayList<>();
        }
        originalTableData.add(new Worker());
        int length = currentTableData.size();
        fireTableRowsInserted(length, length);
        fireTableDataChanged();
    }

    public void RemoveSelectedRow(int row)
    {
        Worker worker = currentTableData.get(row);
        if(worker != null)
        {
            currentTableData.remove(worker);
            originalTableData.remove(worker);
            fireTableRowsDeleted(row, row);
        }
    }


    @Override
    public String getColumnName(int column) {
        return columnNames[column];
    }

    @Override
    public Class<?> getColumnClass(int columnIndex) {
        return columnClasses[columnIndex];
    }

    @Override
    public int getRowCount()
    {
        if(currentTableData == null)
            return 0;

        return currentTableData.size();
    }

    @Override
    public int getColumnCount() { return columnNames.length; }

    @Override
    public Object getValueAt(int row, int col)
    {
        switch (col)
        {
            case 0:
                return currentTableData.get(row).name;
            case 1:
                return currentTableData.get(row).lastName;
            case 2:
                return currentTableData.get(row).workPlace;
            case 3:
                return currentTableData.get(row).job;
            case 4:
                return currentTableData.get(row).earnings;
        }

        return null;
    }

    @Override
    public boolean isCellEditable(int row, int col) { return true; }

    @Override
    public void setValueAt(Object value, int row, int col)
    {
        switch (col)
        {
            case 0:
                currentTableData.get(row).name = String.valueOf(value);
                break;
            case 1:
                currentTableData.get(row).lastName = String.valueOf(value);
                break;
            case 2:
                currentTableData.get(row).workPlace = String.valueOf(value);
                break;
            case 3:
                currentTableData.get(row).job = String.valueOf(value);
                break;
            case 4:
                currentTableData.get(row).earnings = Float.valueOf(value.toString());
                break;
        }
        fireTableCellUpdated(row, col);
    }
}
