package com.example.trainer.database.dao.framework;

import com.example.trainer.database.dao.sqlite.BetterSqliteDAOFactory;
import com.example.trainer.database.dao.sqlite.SqliteExerciseDAO;
import com.example.trainer.database.dao.sqlite.SqliteWorkoutDAO;

public class SqliteDevDAO extends DevelopmentDAO {

    private final SqliteWorkoutDAO workoutDAO;

    SqliteDevDAO(){
        BetterSqliteDAOFactory factory = new BetterSqliteDAOFactory();
        this.workoutDAO = (SqliteWorkoutDAO) factory.createWorkoutDAO();
    }


    @Override
    public void clearDatabase() {
        workoutDAO.clearWholeDatabase();
    }
}
