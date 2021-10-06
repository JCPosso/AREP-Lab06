package co.edu.escuelaing.arep.app.Persistence;

import co.edu.escuelaing.arep.app.Model.User;

public interface userPersistence {
    public void saveUser(User user) throws UserPersistenceException;

}
