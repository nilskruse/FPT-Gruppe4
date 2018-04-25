public class Song implements Song {
    private SimpleStringProperty path, title, album, interpret;

    public String getAlbum(){
        return this.album().get();
    }
}