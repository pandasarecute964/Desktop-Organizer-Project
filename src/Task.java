package src;
public class Task {
    private String title;
    private String category;
    private String notes;
    private boolean isCompleted;
    private String date;



    public Task(String title, String category, String date, String notes) {
        this.title = title;
        this.category = category;
        this.date = date;
        this.isCompleted = false;
         this.notes = notes;

    }


// -------------------- GETTERS -------------------------
    public String getTitle(){
        return title;
    }

   
    

    public String getCategory(){
        return category;
    }

    public String getDate(){
        return date;

    }


    public String getNotes(){
        return notes;
    }


    public boolean IsCompleted(){
        return isCompleted;
    }



    public void setIsCompleted(boolean isCompleted){
        this.isCompleted = isCompleted;
    }


    // -----------------------SETTERS --------------------

    public void setTitle(String title){
        this.title = title;
    }

    public void setDate(String date){
        this.date = date;

    }

    public void setCategory(String category){
        this.category = category;

    }

    public void setNotes(String notes){
        this.notes = notes;

    }



    public String toFileString(){
        return title + "|" + category + "|" + date + "|" + notes + "|" + isCompleted;
    }


    public static Task StringFile(String line){
        String [] parts  = line.split("\\|");

        if(parts.length != 5){
            return null;
        }

        Task task = new Task(parts[0], parts[1], parts[2], parts[3]);
        task.setIsCompleted(Boolean.parseBoolean(parts[4]));


        return task;



    }


@Override
public String toString(){
    
String statusReport = isCompleted ? "[Done]" : "[Open]";

return statusReport + title + " - " + category + " - " + date + " - " + notes;


}









}



