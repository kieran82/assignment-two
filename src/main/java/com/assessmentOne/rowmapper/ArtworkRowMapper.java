package com.assessmentOne.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.jdbc.core.RowMapper;
import com.assessmentOne.entity.Artwork;
import com.assessmentOne.entity.Contributors;
import com.assessmentOne.entity.Movement;

public class ArtworkRowMapper implements RowMapper<Artwork> {

	@Override
	public Artwork mapRow(ResultSet rs, int index) throws SQLException {

		Artwork artwork = new Artwork();
		List<Movement> movements = new ArrayList<>();
		List<Contributors> contributors = new ArrayList<>();

		try {

			artwork.setId(rs.getInt("artId"));
			artwork.setTitle(rs.getString("artTitle"));
			artwork.setGroupTitle(rs.getString("artGroupTitle"));
			artwork.setThumbnailUrl(rs.getString("artthumbnail_url"));
			do {

				Movement movement = new Movement();
				Contributors contributor = new Contributors();

				movement.setId(rs.getInt("movementid"));
				movement.setName(rs.getString("movementname"));
				movements.add(movement);

				artwork.setMovements(movements);

				contributor.setArtist_id(rs.getInt("artistid"));
				contributor.setName(rs.getString("full_name"));

				contributors.add(contributor);

				artwork.setContributors(contributors);

			} while (rs.next());

		} catch (Exception ex) {
		}

		return artwork;

	}
}