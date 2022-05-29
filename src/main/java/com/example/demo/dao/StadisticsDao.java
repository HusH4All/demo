package com.example.demo.dao;

import com.example.demo.model.Stadistics;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
public class StadisticsDao {
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource){ jdbcTemplate= new JdbcTemplate(dataSource);}

    public Stadistics bestSkillAllTime(String table){
        try{
            return jdbcTemplate.queryForObject("select s.name as name, count(x.*) as stat from "+ table +" as x join skilltype as s using(id_s) group by s.name order by stat desc fetch first 1 rows only",
                    new StadisticsRowMapper());
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public Stadistics bestSkillActive(String table){
        try{
            return jdbcTemplate.queryForObject("select s.name as name, count(x.*) as stat from "+ table +" as x join skilltype as s using(id_s) where x.active = true and s.active = true group by s.name order by stat desc fetch first 1 rows only",
                    new StadisticsRowMapper());
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public Stadistics bestStudent(String table){
        try{
            return jdbcTemplate.queryForObject("select s.name as name, count(x.*) as stat from "+ table +" as x join student as s using(id_al) where x.active = true and s.banned = false group by s.name order by stat desc fetch first 1 rows only",
                    new StadisticsRowMapper());
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public Stadistics bestStudentAllTime(String table){
        try{
            return jdbcTemplate.queryForObject("select s.name as name, count(x.*) as stat from "+ table +" as x join student as s using(id_al) where s.banned = false group by s.name order by stat desc fetch first 1 rows only",
                    new StadisticsRowMapper());
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public Stadistics studentWithMoreHours(){
        try{
            return jdbcTemplate.queryForObject("select name, hours as stat from student order by stat desc fetch first 1 rows only",
                    new StadisticsRowMapper());
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public Stadistics mostSkillClosedCollaboration(){
        try{
            return jdbcTemplate.queryForObject("select s.name as name, count(x.*) as stat from collaboration as x join offer as o using(id_o) join skilltype as s using(id_s) where x.state = false group by s.name order by stat desc fetch first 1 rows only",
                    new StadisticsRowMapper());
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public Stadistics mostSkillCollaboratedAllTime(){
        try{
            return jdbcTemplate.queryForObject("select s.name as name, count(x.*) as stat from collaboration as x join offer as o using(id_o) join skilltype as s using(id_s) group by s.name order by stat desc fetch first 1 rows only",
                    new StadisticsRowMapper());
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }

    public Stadistics bestRatedStudent(){
        try{
            return jdbcTemplate.queryForObject("select s.name, sum(score)/count(*) as stat from collaboration as c join offer as o using(id_o) join student as s using(id_al) where c.state = false group by s.name order by stat fetch first 1 rows only;",
                    new StadisticsRowMapper());
        } catch (EmptyResultDataAccessException e){
            return null;
        }
    }
}
