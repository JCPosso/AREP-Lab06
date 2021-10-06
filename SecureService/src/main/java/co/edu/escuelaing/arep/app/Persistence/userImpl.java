package co.edu.escuelaing.arep.app.Persistence;

import co.edu.escuelaing.arep.app.Model.User;
import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentHashMap;

public class userImpl implements userPersistence{
    private final ConcurrentHashMap<String,User> users=new ConcurrentHashMap<>();
    public userImpl () {
        User u1 = new User("juancamilo","123");
        users.put(u1.getHash(),u1);

        User u2 = new User("carlos","456");
        users.put(u2.getHash(),u2);

        User u3 = new User("jhonnatan","789");
        users.put(u3.getHash(),u3);
    }
    @Override
    public void saveUser(User un) throws UserPersistenceException {
        User user= users.putIfAbsent(un.getHash(),un);
        if (user!=null){
            throw new UserPersistenceException("The given user already exists: "+un);
        }
    }
    public ConcurrentHashMap<String,User> getUsers(){
        return users;
    }
}