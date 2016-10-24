package Entity;


public class Photo {

    private int id;  
    private String description;
    private String image;
  
    public Photo(int id, String description, String image) {
        this.id = id;
        this.description = description;
        this.image = image;
      
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    
  

    public Photo() {
    }

    public String toString() {
        return "Photo{" + "id=" + id + ", description=" + description + ", image=" + image + '}';
    }
    


    Photo getPhoto() {
        return null;
    }
}
