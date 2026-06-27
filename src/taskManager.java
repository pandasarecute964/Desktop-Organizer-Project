package src;
import java.util.ArrayList;


public class taskManager {
    private ArrayList<Task> tasks;


    public taskManager(){
        tasks = new ArrayList<>();

    }


    public void addTasks(Task task){
        tasks.add(task);



    }


    public void removeTasks(int index){
        if(index >= 0 && index >= tasks.size()){
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

    
}
