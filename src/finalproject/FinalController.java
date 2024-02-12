package finalproject;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FinalController {

    @FXML
    private GridPane movieContainer;
    @FXML
    private ScrollPane gridContainer;

    private void fetchData(String apiUrl) {
        Task<Void> task = new Task<>() {
            @Override
            protected Void call() throws Exception {
                URL url = new URL(apiUrl);
                HttpURLConnection con = (HttpURLConnection) url.openConnection();

                try {
                    con.setRequestMethod("GET");
                    con.setRequestProperty("accept", "application/json");
                    con.setRequestProperty("Authorization", "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIwMWU5Mzk1ZTlhZTIwODY3MjQ2ZjZjMDU0YzQ4YWVmNSIsInN1YiI6IjY1ODg4ZmIyMDcyMTY2NjcyOWE2MDUxNSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.i4tlDiID_uRdtHEVGTHCBBm5olzHg6kp5-vlBxp-tsE");

                    int status = con.getResponseCode();

                    BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String inputLine;
                    StringBuilder content = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        content.append(inputLine);
                    }
                    in.close();

                    JSONObject jsonObject = new JSONObject(content.toString());
                    JSONArray resultsArray = jsonObject.getJSONArray("results");

                    ObservableList<MovieCard> movieCards = FXCollections.observableArrayList();

                    for (int i = 0; i < resultsArray.length(); i++) {
                        JSONObject movieObject = resultsArray.getJSONObject(i);
                        MovieCard movieCard = new MovieCard(movieObject);
                        movieCard.setMovieData(
                                movieObject.getString("title"),
                                movieObject.getString("overview"),
                                movieObject.getString("release_date"),
                                movieObject.getString("poster_path")
                        );

                       
                        movieCards.add(movieCard);
                    }

                    Platform.runLater(() -> {
                        int column = 0;
                        int row = 0;
                        gridContainer.setVisible(true);
                        gridContainer.setLayoutX(154.0);
                        gridContainer.setLayoutY(62.0);
                        gridContainer.setPrefHeight(561.0);
                        gridContainer.setPrefWidth(950);
                        movieContainer.setPrefHeight(561.0);
                        movieContainer.setPrefWidth(950);
                        movieContainer.getChildren().clear();
                        for (MovieCard movieCard : movieCards) {
                            movieContainer.add(movieCard, column, row);
                            column++;
                            if (column == 4) {
                                column = 0;
                                row++;
                            }
                        }
                    });
                } catch (IOException ex) {
                    ex.printStackTrace();
                } finally {
                    if (con != null) {
                        con.disconnect();
                    }
                }

                return null;
            }
        };

//        task.setOnSucceeded(event -> {
//        });

        task.setOnFailed(event -> {
            Throwable exception = task.getException();
            if (exception != null) {
                exception.printStackTrace();
            }
        });

        Thread thread = new Thread(task);
        thread.start();
    }

    @FXML
    private void run(ActionEvent actionEvent) {
        fetchData("https://api.themoviedb.org/3/movie/now_playing?language=en-US&page=2");
    }

    @FXML
    private void run2(ActionEvent actionEvent) {
        fetchData("https://api.themoviedb.org/3/movie/top_rated?language=en-US&page=3");
    }

    @FXML
    private void run3(ActionEvent actionEvent) {
        fetchData("https://api.themoviedb.org/3/movie/popular?language=en-US&page=2");
    }

    @FXML
    public void movies(MouseEvent mouseEvent) {
        gridContainer.setVisible(false);
    }
}