
package finalproject;

import java.net.MalformedURLException;
import java.net.URL;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import org.json.JSONObject;

public class MovieCard extends HBox {

    private ImageView posterImageView;
    private Label titleLabel;
    private Label overviewLabel;
    private Label releaseDateLabel;
    private Button favorite;

    public MovieCard(JSONObject jsonObject) {
       
        posterImageView = new ImageView();
        posterImageView.setFitWidth(150); 
        posterImageView.setPreserveRatio(true);

        titleLabel = new Label();
        overviewLabel = new Label();
        releaseDateLabel = new Label();
        favorite = new Button("fav");

       
        VBox labelsVBox = new VBox(posterImageView,titleLabel, overviewLabel, releaseDateLabel,favorite);
        labelsVBox.setSpacing(1);
  
        setPadding(new Insets(5));
        setSpacing(5);

  
        getChildren().add(labelsVBox);
    }

    public void setMovieData(String title, String overview, String releaseDate, String posterPath) {
       
        titleLabel.setText("Title: " + title);
//        overviewLabel.setText("Overview: " + overview);
        releaseDateLabel.setText("Release Date: " + releaseDate);

        titleLabel.setTextFill(Color.WHITE); 
        overviewLabel.setTextFill(Color.WHITE); 
        releaseDateLabel.setTextFill(Color.WHITE); 

        try {
           
            Image posterImage = new Image(new URL("https://image.tmdb.org/t/p/w500" + posterPath).toExternalForm());
            posterImageView.setImage(posterImage);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
    }

    public Button getFavoriteButton (){
        return favorite;
    }
}

