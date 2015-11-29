package com.assessmentOne.rowmapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.assessmentOne.entity.Comment;

public class CommentRowMapper  implements RowMapper<Comment> {

	@Override
	public Comment mapRow(ResultSet row, int index) throws SQLException {

		Comment comment = new Comment();
		comment.setUsername((String) (row.getString("username")));
		comment.setArtworkId((String) row.getString("artwork_id"));
		comment.setContent((String) row.getString("comment"));
		comment.setTime((String) row.getString("time"));
	
		return comment;
	}
	
}