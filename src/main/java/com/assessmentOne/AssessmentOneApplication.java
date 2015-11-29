package com.assessmentOne;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.assessmentOne.entity.Artist;
import com.assessmentOne.entity.Artwork;
import com.assessmentOne.repository.ArtistRepository;
import com.assessmentOne.repository.ArtworkRepository;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootApplication
public class AssessmentOneApplication implements CommandLineRunner {

	@Autowired
	ArtistRepository artistRepository;

	@Autowired
	ArtworkRepository artworkRepository;

	public void getJsonFiles(String folder) {

		try {
			Files.walk(Paths.get("src/main/resources/static/json/" + folder)).forEach(filePath -> {
				if (Files.isRegularFile(filePath)) {
					// Another function for parsing here ?
					try {

						if (folder == "artists") {
							JsonToObjectArtist(filePath.toString());
						} else {
							JsonToObjectArtwork(filePath.toString());
						}

					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private void JsonToObjectArtwork(String filePath) throws JsonParseException, JsonMappingException, IOException {

		try {
			Artwork artwork = new ObjectMapper().readValue(new File(filePath), Artwork.class);

			if (artwork.movements.size() != 0) {

				artworkRepository.saveMovement(artwork.movements, artwork.getId());
			}

			if (artwork.contributors.size() != 0) {

				artworkRepository.saveContributors(artwork.contributors, artwork.getId());
			}

			artworkRepository.save(artwork);
		} catch (JsonParseException e) {

			System.out.println("Error parsing the file.");

		} catch (JsonMappingException e) {

			System.out.println("Error mapping to Java object.");

		} catch (IOException e) {

			// System.out.println("Unknown I/O error.");
		}

	}

	public void JsonToObjectArtist(String filePath) throws JsonParseException, JsonMappingException, IOException {
		try {
			Artist artist = new ObjectMapper().readValue(new File(filePath), Artist.class);

			if (artist.movements.size() != 0) {

				artistRepository.saveMovement(artist.movements, artist.getId());
			}

			artistRepository.save(artist);

		} catch (JsonParseException e) {

			System.out.println("Error parsing the file.");

		} catch (JsonMappingException e) {

			System.out.println("Error mapping to Java object.");

		} catch (IOException e) {

			// System.out.println("Unknown I/O error.");
		}

	}

	@Override
	public void run(String... arg0) throws Exception {
		
		getJsonFiles("artists");
		getJsonFiles("artworks");

	}

	public static void main(String[] args) {
		SpringApplication.run(AssessmentOneApplication.class, args);

	}
}
