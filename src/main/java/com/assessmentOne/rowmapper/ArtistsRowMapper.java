package com.assessmentOne.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;
import com.assessmentOne.entity.Artist;

public class ArtistsRowMapper implements RowMapper<Artist> {

	@Override
	public Artist mapRow(ResultSet rs, int index) throws SQLException {
		Artist artist = new Artist();
		artist.setId(rs.getInt("artistid"));
		artist.setFullName(rs.getString("full_name"));
		artist.setGender(rs.getString("gender"));
		return artist;

	}

}
