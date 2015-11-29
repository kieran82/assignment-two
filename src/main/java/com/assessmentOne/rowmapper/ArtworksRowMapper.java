package com.assessmentOne.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.assessmentOne.entity.Artwork;

public class ArtworksRowMapper implements RowMapper<Artwork> {

	@Override
	public Artwork mapRow(ResultSet rs, int index) throws SQLException {

		Artwork artwork = new Artwork();

		artwork.setId(rs.getInt("artId"));
		artwork.setTitle(rs.getString("artTitle"));
		artwork.setGroupTitle(rs.getString("artGroupTitle"));
		artwork.setThumbnailUrl(rs.getString("artthumbnail_url"));

		return artwork;
	}
}