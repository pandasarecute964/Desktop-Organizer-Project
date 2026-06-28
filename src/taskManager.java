package src;
import java.util.ArrayList;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;


public class taskManager {
    private ArrayList<Task> tasks;


    public taskManager(){
        tasks = new ArrayList<>();

    }


    public void addTasks(Task task){
        tasks.add(task);



    }


    public void removeTasks(int index){
        if(index >= 0 && index < tasks.size()){
            tasks.remove(index);
        }


    }



    public Task getTask(int index){

        if(index >= 0 &&  index < tasks.size()){
            return tasks.get(index);

        }

        return null;


    }

    public ArrayList<Task> getTasks(){
        return tasks;
    }


    public void saveTasks(){
        try{
        FileWriter fw = new FileWriter("task.txt");

        for(Task tasks: tasks){
            fw.write(tasks.toFileString() + "\n");
        }

        fw.close();
    } catch(IOException ie){
        System.out.println("Sorry! Could not save task :(");

    }


  }



   public void loadTasksFromFile(){
    try{
        BufferedReader BR = new BufferedReader(new FileReader("task.txt"));

        String line;

        while((line = BR.readLine()) != null){
            Task task = Task.StringFile(line);

            if(task != null){
                tasks.add(task);
            }

        }
        BR.close();
    } 
    
    catch(IOException ie){
        System.out.println("Task not found :(");
    }

    
        

    }
    
}
