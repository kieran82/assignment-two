package com.assessmentOne.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.RowMapper;

import com.assessmentOne.entity.Artist;
import com.assessmentOne.entity.Artwork;
import com.assessmentOne.entity.Birth;
import com.assessmentOne.entity.Movement;
import com.assessmentOne.entity.Place;

public class ArtistRowMapper implements RowMapper<Artist> {

	@Override
	public Artist mapRow(ResultSet rs, int index) throws SQLException {

		Artist artist = new Artist();
		List<Movement> movements = new ArrayList<>();
		List<Artwork> artworks = new ArrayList<>();
		Birth birth = new Birth();
		Place place = new Place();

		try {
			artist.setId(rs.getInt("artistid"));
			artist.setFullName(rs.getString("full_name"));
			artist.setGender(rs.getString("gender"));
			System.out.println(rs.getString("full_name"));

			if (rs.getString("placename") != null) {
				place.setName(rs.getString("placename"));
				birth.setPlace(place);
				artist.setBirth(birth);
			} else {
				place.setName("Birth Place Unknow");
				birth.setPlace(place);
				artist.setBirth(birth);
			}

			do {

				Movement movement = new Movement();
				Artwork artwork = new Artwork();

				movement.setId(rs.getInt("movementid"));
				movement.setName(rs.getString("movementname"));
				movements.add(movement);

				artist.setMovements(movements);

				artwork.setId(rs.getInt("artId"));
				artwork.setTitle(rs.getString("artTitle"));
				artwork.setGroupTitle(rs.getString("artGroupTitle"));
				artwork.setThumbnailUrl(rs.getString("artthumbnail_url"));
				artworks.add(artwork);

				artist.setArtworks(artworks);

			} while (rs.next());

		} catch (Exception ex) {
		}

		return artist;

	}

}
