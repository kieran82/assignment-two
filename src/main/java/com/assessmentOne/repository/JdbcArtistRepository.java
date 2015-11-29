package com.assessmentOne.repository;

//Java Imports
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.assessmentOne.entity.Artist;
import com.assessmentOne.entity.Movement;
import com.assessmentOne.rowmapper.ArtistRowMapper;
import com.assessmentOne.rowmapper.ArtistsRowMapper;

@Repository
public class JdbcArtistRepository implements ArtistRepository {

	// JdbcTemplate
	private JdbcTemplate jdbcTemplate;

	@Autowired
	public JdbcArtistRepository(JdbcTemplate jdbcTemplate) {
		this.jdbcTemplate = jdbcTemplate;
	}

	/*
	 * Update Artists
	 * 
	 * @param Object Artist
	 */
	private void update(Artist artist) {
		if (artist.getGender() == null) {
			artist.setGender("unknown");
		}
		String sql = "UPDATE artists SET full_name = ?, gender = ? WHERE id = ?";
		jdbcTemplate.update(sql, new Object[] { artist.getFullName(), artist.getGender(), artist.getId() });
	}

	/*
	 * Add Artists
	 * 
	 * @param Object Artist
	 */
	private void add(Artist artist) {
		// if gender is a null value
		if ((artist.getGender() == null)) {
			artist.setGender("Unknown Gender");
		}

		String sql = "INSERT INTO artists (id, full_name, gender) VALUES (?,?,?)";
		jdbcTemplate.update(sql, new Object[] { artist.getId(), artist.getFullName(), artist.getGender() });

		
		// SQL to INSERT artists into the database

		String sql_birth = "INSERT INTO birth ( artist_id,name,placeName,placeType) VALUES (?,?,?,?)";
		try {
			// JDBC Template to update the database
			jdbcTemplate.update(sql_birth, new Object[] { artist.getId(), artist.getBirth().getPlace().getName(),
					artist.getBirth().getPlace().getPlaceName(), artist.getBirth().getPlace().getPlaceType() });

		} catch (EmptyResultDataAccessException e) {
			// JDBC Template to update the database

		} catch (NullPointerException e) {
			// I know , but because there was 2 (mostly) but 3 working on it we
			// had to take a shortcut
		}
		System.out.println("Artist Saved");
	}

	/*
	 * Add Movements
	 * 
	 * @param Object Movement
	 */
	public void addMovement(Movement movements, int artistId) {
		// SQL to INSERT movements into the database
		String sql_movement = "INSERT INTO movements (id, name) VALUES (?,?)";
		// JDBC Template to update the database
		jdbcTemplate.update(sql_movement, new Object[] { movements.getId(), movements.getName() });

		String sql_movements = "INSERT INTO artist_movements ( artist_id,movement_id) VALUES (?,?)";

		try {
			jdbcTemplate.update(sql_movements, new Object[] { artistId, movements.getId() });

		} catch (EmptyResultDataAccessException e) {
			// JDBC Template to update the database

		}

		// SQL to INSERT artist movements into the database
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
	 * Find All Artists
	 * 
	 * @param ArrayList Artist
	 * 
	 * @return Array
	 */
	public List<Artist> findAll() {
		String sql = "SELECT a.id as artistid, a.full_name as full_name, a.gender FROM artists a ORDER BY RAND() LIMIT 40  ";
		return jdbcTemplate.query(sql, new ArtistsRowMapper());
	}

	/*
	 * Check if Artist Exists
	 * 
	 * @param Integer id
	 * 
	 * @return Integer
	 */
	public int checkIsThere(int id) {

		int rowCount = this.jdbcTemplate.queryForObject("select count(*) from artists where id = " + id, Integer.class);
		return rowCount;
	}

	/*
	 * Get Total Count
	 * 
	 * @param Integer id
	 * 
	 * @param String sql
	 * 
	 * @return Integer
	 */
	public int getTotalCount(int id, String sql) {

		int rowCount = this.jdbcTemplate.queryForObject(sql + id, Integer.class);

		return rowCount;
	}

	/*
	 * Get Artist by ID
	 * 
	 * @param Integer id
	 * 
	 * @return Object
	 */
	public Artist findById(int id) {
		Artist artist = null;
		// This is for movements, artwork and birth
		String sql1 = "SELECT DISTINCT b.name as placename , art.title as artTitle,art.group_title as artGroupTitle,art.thumbnail_url as artthumbnail_url, art.id as artId,artists.id as artistid, artists.full_name as full_name, artists.gender as gender,  m.id as movementid, m.name as movementname "
				+ " FROM artists " + "LEFT JOIN artist_artwork aa " + "ON aa.artist_id = artists.id "
				+ "Left JOIN artwork art " + "ON aa.artwork_id  = art.id " + "Left JOIN artist_movements am "
				+ "ON am.artist_id = artists.id " + "Left JOIN movements m " + "ON m.id = am.movement_id "
				+ "Left JOIN birth b " + "ON b.artist_id = artists.id " + "WHERE artists.id = ? LIMIT 40";
 
		artist = jdbcTemplate.queryForObject(sql1, new Object[] { id }, new ArtistRowMapper());

		return artist;
	}

	/*
	 * Save Movement
	 * 
	 * @param ArrayList Movement
	 * 
	 * @param Integer artistId
	 */
	public void saveMovement(List<Movement> movements, int artistId) {

		for (int i = 0; i < movements.size(); i++) {
			Movement m = movements.get(i);
			if (findMovementById(m.getId()) != 0) {

			} else {
				addMovement(m, artistId);
			}
		}
	}

	/*
	 * Save Artist
	 * 
	 * @param Object Artist
	 */
	public void save(Artist artist) {

		if (checkIsThere(artist.getId()) != 0) {
			update(artist);
		} else {
			add(artist);
		}
	}
}
