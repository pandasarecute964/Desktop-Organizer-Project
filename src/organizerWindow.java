package src;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Locale.Category;



// ------------------- CONSTRUCTOR ------------------------
public class organizerWindow extends JFrame {
    private taskManager taskManager;
    private DefaultListModel<Task> TasksListModel;
    private JList<Task> TasksList;

    private JTextField TitleField;
    private JTextField CategoryField;
    private JTextField DateField;
    private JTextArea NotesArea;

    public organizerWindow() {
        taskManager = new taskManager();
        taskManager.loadTasksFromFile();
    


    setTitle("Desktop Organizer <3");
    setSize(900, 700);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setLocationRelativeTo(null);

    createComponents();
    layoutComponents();
    AlertTasks();

    }

    // ------------ CREATE COMPONENTS METHOD -----------------------
    private void createComponents() {
        TasksListModel = new DefaultListModel<>();
        TasksList = new JList<>(TasksListModel);


        TitleField = new JTextField();
        CategoryField = new JTextField();
        DateField = new JTextField();
        NotesArea = new JTextArea(4, 20);
        NotesArea.setLineWrap(true);
        NotesArea.setWrapStyleWord(true);

         TasksList.addListSelectionListener(e -> {
            if(!e.getValueIsAdjusting()){
                LoadSelectedTaskFields();
            }
        });

         for(Task task : taskManager.getTasks()){
            TasksListModel.addElement(task);
        }


    }

    // ------------------- LAYOUTCOMPONENTS METHOD --------------------------
    private void layoutComponents() {
        JPanel bottomPanel = new JPanel(new BorderLayout(8, 8));
        bottomPanel.add(Box.createVerticalStrut(10), BorderLayout.NORTH);
        JPanel inputPanel = new JPanel(new GridLayout(4, 2, 10, 8));

        inputPanel.add(new JLabel("Title: "));
        inputPanel.add(TitleField);

        inputPanel.add(new JLabel("Category: "));
        inputPanel.add(CategoryField);

        inputPanel.add(new JLabel("Date: "));
        inputPanel.add(DateField);

        inputPanel.add(new JLabel("Notes: "));
        inputPanel.add(NotesArea);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 10));
        

        JButton addButton = new JButton("Add Task: ");
        addButton.addActionListener(e -> addTask());

        JButton compButton = new JButton("Mark Complete: ");
        compButton.addActionListener(e -> markTaskComplete());

        JButton saveButton = new JButton("Save Task!");
        saveButton.addActionListener(e -> saveTasks());

        JButton deleteButton = new JButton("Delete Task");
        deleteButton.addActionListener(e -> deleteTask());

        JButton editButton = new JButton("Edit Task");
        editButton.addActionListener(e -> editTask());

        buttonPanel.add(addButton);
        buttonPanel.add(compButton);
        buttonPanel.add(saveButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(editButton);


        bottomPanel.add(inputPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(new JScrollPane(TasksList), BorderLayout.CENTER);


        add(bottomPanel, BorderLayout.SOUTH);


    }

// ---------------------- ADDTASK METHOD ----------------------------
    private void addTask(){
         String title = TitleField.getText();
         String category = CategoryField.getText();
         String date = DateField.getText();
         String notes = NotesArea.getText();


         if(title.trim().isEmpty()){ // replace this when upgraded to java 11 with isBlank()
            JOptionPane.showMessageDialog( this, "PLease enter a title for the task: ");

            return;
         }



         Task task = new Task(title, category, date, notes);
         taskManager.addTasks(task);
         TasksListModel.addElement(task);

         taskManager.saveTasks();
          AlertifTaskDue(task);

         TitleField.setText("");
         CategoryField.setText("");
         DateField.setText("");
         NotesArea.setText("");

    }

    // ----------------- MARK TASK COMPLETE METHOD ----------------------------

    private void markTaskComplete(){
        int selectedIndex = TasksList.getSelectedIndex();



        if(selectedIndex == -1){
            JOptionPane.showMessageDialog(this, "Please select a task: ");

            return;
        }

        Task task = taskManager.getTask(selectedIndex);

        task.setIsCompleted(true);

        TasksListModel.set(selectedIndex, task);

        taskManager.saveTasks();


    }

// -------------------- SAVE TASKS METHOD ------------------------
    private void saveTasks(){
        taskManager.saveTasks();

        JOptionPane.showMessageDialog(this, "Saved Task!");
    }

// ------------------------- DELETE TASKS METHOD -------------------------------

    private void deleteTask(){

        int selectedIndex = TasksList.getSelectedIndex();

        if(selectedIndex == -1){
            JOptionPane.showMessageDialog(this, "Please select a task first! ");
            return;
        }

        taskManager.removeTasks(selectedIndex);
        TasksListModel.remove(selectedIndex);

            taskManager.saveTasks();


    JOptionPane.showMessageDialog(this, "Task deleted!");
    }

    // ------------------------ ALERTS METHOD ------------------------------

private void AlertTasks(){
    LocalDate today = LocalDate.now();

    String message = "";

    for(Task task : taskManager.getTasks()){
        try{
             LocalDate taskDate = LocalDate.parse(task.getDate().trim());

             if(!task.IsCompleted() && (taskDate.isEqual(today) || taskDate.isBefore(today))){
                message += task.getTitle() + " is due on " + task.getDate() + "\n";
             }
        } catch(DateTimeParseException DTPE){
            JOptionPane.showMessageDialog( this, "Please enter the date as YYYY-MM-DD");
            return;

        }
    }

    if(!message.isEmpty()){
        JOptionPane.showMessageDialog(this, message, "Task Reminder!", JOptionPane.WARNING_MESSAGE);
    }

}


private void AlertifTaskDue(Task task){
     try{
         LocalDate today = LocalDate.now();
          LocalDate taskDate = LocalDate.parse(task.getDate().trim());

          if(!task.IsCompleted() && (taskDate.isEqual(today) || taskDate.isBefore(today))){
            JOptionPane.showMessageDialog(this, task.getTitle() + " is due on " + task.getDate(), "Task Reminder!", JOptionPane.WARNING_MESSAGE);
          }

     } catch(DateTimeParseException DTPE){
            JOptionPane.showMessageDialog( this, "Please enter the date as YYYY-MM-DD");
            return;

     }

    

}

// ----------------------- EDIT TASKS METHOD -------------------------

private void editTask(){
    int selectedIndex = TasksList.getSelectedIndex();

    if(selectedIndex == -1){
        JOptionPane.showMessageDialog(this, "Please select a task first!");

        return;
    }

    if(TitleField.getText().trim().isEmpty()){
        JOptionPane.showMessageDialog(this, "Please enter a title for selected task!");
        return;
    }

    Task task = taskManager.getTask(selectedIndex);


    task.setTitle(TitleField.getText());
    task.setDate(DateField.getText());
    task.setNotes(NotesArea.getText());
    task.setCategory(CategoryField.getText());

    TasksListModel.set(selectedIndex, task);

    taskManager.saveTasks();

   
}

// --------------------------- LOAD TASKS INFO METHOD --------------------

private void LoadSelectedTaskFields(){
    Task task = TasksList.getSelectedValue();

    if(task == null){
        return;
    }

    TitleField.setText(task.getTitle());
    DateField.setText(task.getDate());
    NotesArea.setText(task.getNotes());
    CategoryField.setText(task.getCategory());

}






    












    
    
}
