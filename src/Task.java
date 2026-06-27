package src;
public class Task {
    private String title;
    private String category;
    private boolean isCompleted;
    private String date;



    public Task(String title, String category, String date) {
        this.title = title;
        this.category = category;
        this.date = date;
        this.isCompleted = false;

    }



    public String getTitle(){
        return title;
    }

   
    

    public String getCategory(){
        return category;
    }

    public String getDate(){
        return date;

    }


    public boolean IsCompleted(){
        return isCompleted;
    }



    public void setIsCompleted(boolean isCompleted){
        this.isCompleted = isCompleted;
    }



@Override
public String toString(){
    
String statusReport = isCompleted ? "[Done]" : "[Open]";

return statusReport + title + " - " + category + " - " + date;


}









}



