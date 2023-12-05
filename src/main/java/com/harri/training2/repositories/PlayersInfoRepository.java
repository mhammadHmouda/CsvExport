package com.harri.training2.repositories;

import com.harri.training2.models.PlayerInfo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PlayersInfoRepository extends GeneralFifaRepository<PlayerInfo> {

    public PlayersInfoRepository(JdbcTemplate jdbcTemplate) {
        super(jdbcTemplate, PlayerInfo.class);
    }

    @Override
    protected String select() {
        return "SELECT p.*";
    }

    @Override
    protected String join() {
        return " FROM players_big p LIMIT 80";
    }

}
