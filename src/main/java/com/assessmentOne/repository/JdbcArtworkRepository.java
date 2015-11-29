package com.assessmentOne.repository;

//Java Imports
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import com.assessmentOne.entity.Artwork;
import com.assessmentOne.entity.Comment;
import com.assessmentOne.entity.Contributors;
import com.assessmentOne.entity.Movement;
import com.assessmentOne.rowmapper.ArtworkRowMapper;
import com.assessmentOne.rowmapper.ArtworksRowMapper;
import com.assessmentOne.rowmapper.CommentRowMapper;

@Repository
public class JdbcArtworkRepository implements ArtworkRepository {
	// JdbcTemplate
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcArtworkRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/*
	 * Add Artist
	 * 
	 * @param Object Artwork
	 */
	public void add(Artwork artwork) {

		if (artwork.getGroupTitle() == null) {
			artwork.setGroupTitle("Unknown Title ");
		}

		if (artwork.getThumbnailUrl() == null) {
			artwork.setThumbnailUrl("http://torontoist.com/wp-content/uploads/2011/11/missing-art-640x479.jpg");
		}

		String sql = "INSERT INTO artwork (id, title, group_title,thumbnail_url) VALUES (?,?,?,?)";
		jdbcTemplate.update(sql, new Object[] { artwork.getId(), artwork.getTitle(), artwork.getGroupTitle(),
				artwork.getThumbnailUrl() });
		
		System.out.println("Artwork Saved");
	}

	/*
	 * Update Artwork
	 * 
	 * @param Object Artwork
	 */
	private void update(Artwork artwork) {

		if (artwork.getGroupTitle() == null) {
			artwork.setGroupTitle("Unknow Title");
		}

		if (artwork.getThumbnailUrl() == null) {
			artwork.setThumbnailUrl("http://torontoist.com/wp-content/uploads/2011/11/missing-art-640x479.jpg");
		}

		String sql = "UPDATE artwork SET title = ?, group_title = ?, thumbnail_url = ? WHERE id = ?";
		jdbcTemplate.update(sql, new Object[] { artwork.getTitle(), artwork.getGroupTitle(), artwork.getThumbnailUrl(),
				artwork.getId() });
	}

	/*
	 * checkIsThere
	 * 
	 * @param Integer id
	 * 
	 * @return Integer
	 */
	public int checkIsThere(int id) {

		int rowCount = this.jdbcTemplate.queryForObject("select count(*) from artwork where id = " + id, Integer.class);
		return rowCount;

	}

	/*
	 * Add Movements
	 * 
	 * @param Object Movement
	 * 
	 * @param Integer artworkid
	 */
	public void addMovement(Movement movements, int artworkid) {

		String sql_movements = "INSERT INTO artwork_movements (artwork_id,movement_id ) VALUES (?,?)";
		jdbcTemplate.update(sql_movements, new Object[] { artworkid, movements.getId() });

	}

	/*
	 * Find Movements
	 * 
	 * @param Integer id
	 * 
	 * @return Integer
	 */
	public int findMovementById(int id) {

		int rowCount = this.jdbcTemplate.queryForObject("select count(*) from movements where id = " + id,
				Integer.class);

		return rowCount;
	}

	/*
	 * Get Artwork by ID
	 * 
	 * @param Integer id
	 * 
	 * @return Object
	 */
	public Artwork findById(int id) {

		String sql = "SELECT DISTINCT  art.title as artTitle,art.group_title as artGroupTitle,art.thumbnail_url as artthumbnail_url, art.id as artId,artists.id as artistid, artists.full_name, artists.gender as gender,  m.id as movementid, m.name as movementname "
				+ " FROM artwork art" + " LEFT JOIN artist_artwork  aa" + " ON aa.artwork_id = art.id"
				+ " Left JOIN artists  " + " ON aa.artist_id  = artists.id " + " Left JOIN artist_movements am"
				+ " ON am.artist_id = artists.id " + " Left JOIN movements m" + " ON m.id = am.movement_id"
				+ " WHERE art.id = ? ";

		Artwork artwork = jdbcTemplate.queryForObject(sql, new Object[] { id }, new ArtworkRowMapper());
		return artwork;

	}

	/*
	 * Find All Artwork
	 * 
	 * @param ArrayList Artwork
	 * 
	 * @return Array
	 */
	public List<Artwork> findAll() {
		String sql = "SELECT  art.title as artTitle,art.group_title as artGroupTitle,art.thumbnail_url as artthumbnail_url, art.id as artId FROM artwork art ORDER BY RAND() LIMIT 40 ";
		return jdbcTemplate.query(sql, new ArtworksRowMapper());

	}

	/*
	 * Save Artwork
	 * 
	 * @param Object Artwork
	 */
	public void save(Artwork artwork) {
		if (checkIsThere(artwork.getId()) != 0) {
			update(artwork);
		} else {
			add(artwork);
		}
	}

	/*
	 * Save Movement
	 * 
	 * @param ArrayList Movement
	 * 
	 * @param Integer artworkid
	 * 
	 */
	public void saveMovement(List<Movement> movements, int artworkid) {

		for (int i = 0; i < movements.size(); i++) {
			Movement m = movements.get(i);

			addMovement(m, artworkid);
		}
	}

	/*
	 * Save Contributors
	 * 
	 * @param ArrayList Contributors
	 * 
	 * @param Integer id
	 * 
	 */
	public void saveContributors(List<Contributors> contributors, int id) {

		for (int i = 0; i < contributors.size(); i++) {
			Contributors c = contributors.get(i);

			addContributors(c, id);

		}
	}

	/*
	 * addComment
	 * 
	 * @param Object Comment
	 * 
	 */
	@Override
	public void addComment(Comment comment) {

		String sql_comment = "INSERT INTO comments (artwork_id ,comment,username) VALUES (?,?,?)";
		jdbcTemplate.update(sql_comment,
				new Object[] { comment.getArtworkId(), comment.getContent(), comment.getUsername() });

	}

	/*
	 * Save Contributors
	 * 
	 * @param ArrayList Contributors
	 * 
	 * @param Integer id
	 * 
	 */
	public void addContributors(Contributors c, int id) {

		String sql = "INSERT INTO artist_artwork (artist_id,artwork_id) VALUES (?,?)";
		jdbcTemplate.update(sql, new Object[] { c.getArtist_id(), id });

	}

	/*
	 * Save Contributors
	 * 
	 * @param ArrayList Comment
	 * 
	 * @param String artworkId
	 * 
	 * @return ArrayList
	 */
	@Override
	public List<Comment> getComments(String artworkId) {

		String sql = "SELECT comment,username,artwork_id,time FROM comments WHERE artwork_id = " + artworkId + "";
		try {
			return jdbcTemplate.query(sql, new CommentRowMapper());
		} catch (EmptyResultDataAccessException e) {

			return null;

		}
	}
}
