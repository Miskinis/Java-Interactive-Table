package com.company;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.table.TableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class UserGUI extends JFrame
{
    private JPanel rootPanel;
    private JButton ReadFileButton;
    private JButton WriteFileButton;
    private JTable DataTable;
    private JLabel TextLabel;
    private JTextField ValueFilterTextField;
    private JLabel FilterFieldLabel;
    private JButton AddRowButton;
    private JButton RemoveRowButton;

    WorkerTableModel tableModel;

    public UserGUI()
    {
        setTitle("Worker Management");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        tableModel = new WorkerTableModel();
        DataTable.setModel(tableModel);

        JFileChooser fileChooser = new JFileChooser();
        FileNameExtensionFilter xmlFilter = new FileNameExtensionFilter("json files (*.json)", "json");
        // add filters
        fileChooser.addChoosableFileFilter(xmlFilter);
        fileChooser.setFileFilter(xmlFilter);

        add(rootPanel);

        ReadFileButton.addActionListener(new ActionListener()
        {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e)
            {
                fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
                int result = fileChooser.showOpenDialog(rootPanel);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();

                    tableModel.setCurrentTableData(Main.ReadJsonFile(selectedFile.getAbsolutePath()));
                }
            }
        });
        WriteFileButton.addActionListener(new ActionListener()
        {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                fileChooser.setDialogTitle("Specify a file to save");

                int userSelection = fileChooser.showSaveDialog(rootPanel);

                if (userSelection == JFileChooser.APPROVE_OPTION) {
                    File fileToSave = fileChooser.getSelectedFile();
                    Main.WriteJsonFile(fileToSave.getAbsolutePath(), tableModel.currentTableData);
                }
            }
        });
        ValueFilterTextField.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.FilterDataByEarnings(ValueFilterTextField.getText());
            }
        });
        AddRowButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.addEmptyRow();
            }
        });
        RemoveRowButton.addActionListener(new ActionListener() {
            /**
             * Invoked when an action occurs.
             *
             * @param e the event to be processed
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                tableModel.RemoveSelectedRow(DataTable.getSelectedRow());
            }
        });
    }
}
