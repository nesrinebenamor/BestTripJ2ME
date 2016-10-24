package Entity;


public class Video {

    private int id;  
    private String description;
    private String vid;
  
    public Video(int id, String description, String vid) {
        this.id = id;
        this.description = description;
        this.vid = vid;
      
    }

    public int getId() {
        return id;
    }

    public void setId(String id) {
        this.id = Integer.parseInt(id);
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVid() {
        return vid;
    }

    public void setVid(String vid) {
        this.vid = vid;
    }

   
  

    public Video() {
    }

    public String toString() {
        return "Video{" + "id=" + id + ", description=" + description + ", video=" + vid + '}';
    }
    


    Video getVideo() {
        return null;
    }
}
