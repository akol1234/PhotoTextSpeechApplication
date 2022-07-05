package foodsqlitedemo.quocnguyen.com.foodsqlitedemo;



public class Photo {
    private int id;
    private String desc;
    private byte[] image;

    public Photo(String desc, byte[] image, int id) {
        this.desc = desc;
        this.image = image;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}
