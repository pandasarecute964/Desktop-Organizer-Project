package src;
import javax.swing.*;
import java.awt.*;


public class organizerWindow extends JFrame {
    private taskManager taskManager;
    private DefaultListModel<Task> TasksListModel;
    private JList<Task> TasksList;

    private JTextField TitleField;
    private JTextField CategoryField;
    private JTextField DateField;

    public organizerWindow() {
        taskManager = new taskManager();
    


    setTitle("Desktop Organizer <3");
    setSize(400, 300);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    createComponents();
    layoutComponents();

    }

    private void createComponents() {
        TasksListModel = new DefaultListModel<>();
        TasksList = new JList<>(TasksListModel);

        TitleField = new JTextField();
        CategoryField = new JTextField();
        DateField = new JTextField();

    }

    private void layoutComponents() {
        JPanel inputPanel = new JPanel(new GridLayout(4, 4, 8, 8));

        inputPanel.add(new JLabel("Title: "));
        inputPanel.add(TitleField);

        inputPanel.add(new JLabel("Category: "));
        inputPanel.add(CategoryField);

        inputPanel.add(new JLabel("Date: "));
        inputPanel.add(DateField);

        JButton addButton = new JButton("Add Task: ");
        addButton.addActionListener(e -> addTask());

        JButton compButton = new JButton("Mark Complete: ");
        compButton.addActionListener(e -> markTaskComplete());

        inputPanel.add(addButton);
        inputPanel.add(compButton);


        add(new JScrollPane(TasksList), BorderLayout.CENTER);

        add(inputPanel, BorderLayout.SOUTH);

    }


    private void addTask(){
         String title = TitleField.getText();
         String category = CategoryField.getText();
         String date = DateField.getText();


         if(title.trim().isEmpty()){ // replace this when upgraded to java 11 with isBlank()
            JOptionPane.showMessageDialog( this, "PLease enter a title for the task: ");

            return;
         }



         Task task = new Task(title, category, date);
         taskManager.addTasks(task);

         TasksListModel.addElement(task);

         TitleField.setText("");
         CategoryField.setText("");
         DateField.setText("");

    }

    private void markTaskComplete(){
        int selectedIndex = TasksList.getSelectedIndex();



        if(selectedIndex == -1){
            JOptionPane.showMessageDialog(this, "Please select a task: ");

            return;
        }

        Task task = taskManager.getTask(selectedIndex);

        task.setIsCompleted(true);

        TasksListModel.set(selectedIndex, task);




    }










    
    
}
