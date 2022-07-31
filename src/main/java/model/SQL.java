package model;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class SQL
{
    private static final Statement statement = Repository.statement;
    public static void insertNewUser (String username,String password,int SQNumber,String answer,String accountType) throws SQLException {
        String sql = "INSERT INTO users (UserName, Password, SQNumber, Answer, AccountType,ProfileImage) values ('" + username+"','"+password + "','" + SQNumber + "','" + answer + "','" + accountType + "','default profile.jpg')";
        statement.execute(sql);
    }
    public static String getProfileImageByID(int ID) throws SQLException
    {
        String profileImage = " ";
        String sql = "SELECT ProfileImage FROM users WHERE ID = '"+ID+"';";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next())
        {
            profileImage=resultSet.getString("ProfileImage");
        }
        return profileImage;
    }
    public static int getIDByUserName (String username) throws SQLException
    {
        String sql = "SELECT ID from users where UserName = '" + username + "';";
        ResultSet resultSet = statement.executeQuery(sql);
        if (!resultSet.next())
        {
            return -1;
        }
        return resultSet.getInt("ID");
    }
    public static String getUserNameByID (int ID) throws SQLException
    {
        String sql = "SELECT UserName FROM users WHERE ID = '"+ID+"';";
        ResultSet resultSet=statement.executeQuery(sql);
        if(!resultSet.next())
        {
            return " ";
        }
        return resultSet.getString("UserName");
    }
    public static String getPasswordByID (int ID) throws SQLException
    {
        String sql = "SELECT Password from users where ID = '" + ID + "';";
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        return resultSet.getString("Password");
    }
    public static int getSQNumberByID (int ID) throws SQLException
    {
        String sql = "SELECT SQNumber FROM users WHERE ID = '" + ID + "';";
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        return resultSet.getInt("SQNumber");
    }
    public static String getAnswerByID (int ID) throws SQLException
    {
        String sql = "SELECT answer FROM users WHERE ID = '" + ID + "';";
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        return resultSet.getString("answer");
    }
    public static boolean getBoolADByID(int ID) throws SQLException
    {
        String sql = "SELECT AccountType FROM users WHERE ID = '"+ ID + "';";
        ResultSet resultSet = statement.executeQuery(sql);
        resultSet.next();
        if(resultSet.getString("AccountType").equals("personal"))
        {
            return false;
        }
        else
        {
            return true;
        }
    }
    public static void setNewPasswordByID (int ID,String newPassword) throws SQLException
    {
        String sql = "UPDATE users SET Password = '" +newPassword + "' WHERE ID = '" + ID + "';" ;
        statement.execute(sql);
    }
    public static ArrayList<String> getFollowingsUserNameByID (int ID) throws SQLException
    {
        ArrayList<Integer> followingsID=new ArrayList<Integer>();
        ArrayList<String> followingsUserName=new ArrayList<String>();
        String sql = "SELECT FollowingID FROM relations WHERE FollowerID = '"+ID+"';";
        ResultSet resultSet=statement.executeQuery(sql);
        while (resultSet.next())
        {
            followingsID.add(resultSet.getInt("FollowingID"));
        }
        int i;
        for(i=followingsID.size()-1;i>=0;i--)
        {
            followingsUserName.add(SQL.getUserNameByID(followingsID.get(i)));
        }
        return followingsUserName;
    }
    public static ArrayList<Integer> getFollowingsPostsIDByID (int ID) throws SQLException
    {
        ArrayList<Integer> postsID=new ArrayList<Integer>();
        ArrayList<Integer> followingsID=SQL.getFollowingsIDByID(ID);
        if(followingsID.size()!=0)
        {
            String sql = "SELECT ID FROM contents WHERE";
            int i;
            for (i = 0; i < followingsID.size(); i++)
            {
                sql += " ( UserID = '" + followingsID.get(i) + "' AND BoolPost = 1)";
                if (i != followingsID.size() - 1)
                {
                    sql += " OR";
                }
            }
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next())
            {
                postsID.add(resultSet.getInt("ID"));
            }
        }
        return postsID;
    }
    public static ArrayList<Integer> getFollowingsIDByID(int ID) throws SQLException
    {
        ArrayList<Integer> followingsID=new ArrayList<Integer>();
        String sql = "SELECT FollowingID FROM relations WHERE FollowerID = '"+ID+"';";
        ResultSet resultSet=statement.executeQuery(sql);
        while(resultSet.next())
        {
            followingsID.add(resultSet.getInt("FollowingID"));
        }
        return followingsID;
    }
    public static ArrayList<String> getFollowersUserNameByID(int ID) throws SQLException
    {
        ArrayList<Integer> followersID=new ArrayList<Integer>();
        ArrayList<String> followersUserName=new ArrayList<String>();
        String sql = "SELECT FollowerID FROM relations WHERE FollowingID = '"+ID+"';";
        ResultSet resultSet=statement.executeQuery(sql);
        while (resultSet.next())
        {
            followersID.add(resultSet.getInt("FollowerID"));
        }
        int i;
        for(i=followersID.size()-1;i>=0;i--)
        {
            followersUserName.add(SQL.getUserNameByID(followersID.get(i)));
        }
        return followersUserName;
    }
    public static void follow(int followerID,int followingID) throws SQLException
    {
        String sql = "INSERT INTO relations values ('" + followerID +"','"+followingID+"');";
        statement.execute(sql);
    }
    public static void unfollow(int followerID,int followingID) throws SQLException
    {
        String sql = "DELETE FROM relations WHERE FollowerID = '"+followerID+"' AND FollowingID = '"+followingID+"';";
        statement.execute(sql);
    }
    public static void createNewPost(int userID,String content,boolean AD,String image) throws SQLException
    {
        int ad;
        if(AD==true)
        {
            ad=1;
        }
        else
        {
            ad=0;
        }
        String sql = "INSERT INTO contents (UserID,Content,BoolAdPost,BoolPost,ContentID,Image) VALUES ('"+userID+"','"+content+"','"+ad+ "','1','0','"+image+"');";
        statement.execute(sql);
    }
    public static ArrayList<Integer> getPostsIDByUserID(int UserID) throws SQLException
    {
        ArrayList<Integer> postsID=new ArrayList<Integer>();
        String sql = "SELECT ID FROM contents Where UserID = '"+UserID+"' AND BoolPost = '1';";
        ResultSet resultSet=statement.executeQuery(sql);
        while(resultSet.next())
        {
            postsID.add(resultSet.getInt("ID"));
        }
        return postsID;
    }
    public static void removePostByID(int ContentID) throws SQLException
    {
        String sql = "DELETE FROM contents WHERE ID = '"+ContentID+"';";
        statement.execute(sql);
    }
    public static String getContentByContentID(int ContentID) throws SQLException
    {
        String Content = " ";
        String sql = "SELECT Content FROM contents WHERE ID = '"+ContentID+"';";
        ResultSet resultSet=statement.executeQuery(sql);
        while (resultSet.next())
        {
            Content = resultSet.getString("Content");
        }
        return Content;
    }
    public static String getImageByContentID(int ContentID) throws SQLException
    {
        String image = " ";
        String sql = "SELECT Image FROM contents WHERE ID = '"+ContentID+"'";
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next())
        {
            image = resultSet.getString("Image");
        }
        return image;
    }
    public static void like(int UserID,int ContentID,String Date) throws SQLException
    {
        String sql = "INSERT INTO likes (UserID,ContentID,Date) VALUES ('"+UserID+"','"+ContentID+"','"+Date+"');";
        statement.execute(sql);
    }
    public static void unlike(int UserID,int ContentID) throws SQLException
    {
        String sql = "DELETE FROM likes WHERE UserID = '"+UserID+"' AND ContentID = '"+ContentID+"';";
        statement.execute(sql);
    }
    public static boolean liked(int UserID,int ContentID) throws SQLException
    {
        String sql = "SELECT *FROM likes WHERE ContentID = "+ContentID;
        ResultSet resultSet=statement.executeQuery(sql);
        boolean liked = false;
        while(resultSet.next())
        {
            if(resultSet.getInt("UserID")==UserID)
            {
                liked=true;
                break;
            }
        }
        return liked;
    }
    public static int likesNumber(int ContentID) throws SQLException
    {
        int likes = 0;
        String sql = "SELECT *FROM likes WHERE ContentID = "+ContentID;
        ResultSet resultSet=statement.executeQuery(sql);
        while (resultSet.next())
        {
            likes++;
        }
        return likes;
    }
    public static void comment(int UserID,int ContentID,String Content) throws SQLException
    {
        String sql = "INSERT INTO contents ( UserID , Content , BoolAdPost , BoolPost , ContentID ,Image ) VALUES ('"+UserID+"','"+Content+"','0','0','"+ContentID+"','0')";
        statement.execute(sql);
    }
    public static ArrayList<Integer> getCommentsIDOfPost(int ContentID) throws SQLException
    {
        ArrayList<Integer> contentsID = new ArrayList<Integer>();
        String sql = "SELECT ID FROM contents WHERE ContentID = '"+ContentID+"';";
        ResultSet resultSet=statement.executeQuery(sql);
        while (resultSet.next())
        {
            contentsID.add(resultSet.getInt("ID"));
        }
        return contentsID;
    }
    public static void reply(int UserID,int ContentID,String ReplyContent) throws SQLException
    {
        String sql = "INSERT INTO contents (UserID,Content,BoolAdPost,BoolPost,ContentID,Image) VALUES ('"+UserID+"','"+ReplyContent+"','0','0','"+ContentID+"','0');";
        statement.execute(sql);
    }
    public static ArrayList<Integer> getRepliesIDOfComment(int ContentID) throws SQLException
    {
        ArrayList<Integer> repliesID = new ArrayList<Integer>();
        String sql = "SELECT ID FROM contents WHERE ContentID = '"+ContentID+"';";
        ResultSet resultSet = statement.executeQuery(sql);
        while(resultSet.next())
        {
            repliesID.add(resultSet.getInt("ID"));
        }
        return repliesID;
    }
    public static ArrayList<Integer> getSuggestedUserIDsByUserID(int UserID) throws SQLException
    {
        ArrayList<Integer> followingIDs = SQL.getFollowingsIDByID(UserID);
        ArrayList<Integer> suggestedUsersIDs = new ArrayList<Integer>();
        int i,j;
        ArrayList<Integer> followings = new ArrayList<Integer>();
        for(i=0;i<followingIDs.size();i++)
        {
            followings = SQL.getFollowingsIDByID(followingIDs.get(i));
            suggestedUsersIDs.addAll(followings);
        }
        for(i=0;i<followingIDs.size();i++)
        {
            for(j=0;j<suggestedUsersIDs.size();j++)
            {
                if(followingIDs.get(i).equals(suggestedUsersIDs.get(j)))
                {
                    suggestedUsersIDs.remove(j);
                    j--;
                }
            }
        }
        for(i=0;i<suggestedUsersIDs.size();i++)
        {
            for(j=i+1;j<suggestedUsersIDs.size();j++)
            {
                if(suggestedUsersIDs.get(i).equals(suggestedUsersIDs.get(j)))
                {
                    suggestedUsersIDs.remove(j);
                    j--;
                }
            }
        }
        for(i=0;i<suggestedUsersIDs.size();i++)
        {
            if(suggestedUsersIDs.get(i).equals(UserID))
            {
                suggestedUsersIDs.remove(i);
                break;
            }
        }
        return suggestedUsersIDs;
    }
    public static void profileView(int UserID,String Date) throws SQLException
    {
        String sql = "INSERT INTO profileview (UserID,Date) VALUES ('"+UserID+"','"+Date+"');";
        statement.execute(sql);
    }
    public static ArrayList<String> getProfileViewsByID(int UserID) throws SQLException
    {
        String sql = "SELECT Date FROM profileview WHERE UserID = '"+UserID+"';";
        ArrayList<String> dates = new ArrayList<String>();
        ArrayList<Integer> views = new ArrayList<Integer>();
        ArrayList<String> profileViews = new ArrayList<String>();
        ResultSet resultSet = statement.executeQuery(sql);
        while (resultSet.next())
        {
            dates.add(resultSet.getString("Date"));
        }
        int i,j;
        for(i=0;i< dates.size();i++)
        {
            views.add(1);
            for(j=i+1;j<dates.size();j++)
            {
                if(dates.get(i).equals(dates.get(j)))
                {
                    views.set(i, views.get(i)+1);
                    dates.remove(i);
                    j--;
                }
            }
        }
        for (i=0;i<dates.size();i++)
        {
            profileViews.add(dates.get(i)+" : "+views.get(i)+" views");
        }
        return profileViews;
    }
    public static ArrayList<String> getLikesStats(int ContentID) throws SQLException
    {
        String sql = "SELECT Date From likes WHERE ContentID = '"+ContentID+"';";
        ResultSet resultSet = statement.executeQuery(sql);
        ArrayList<String> dates = new ArrayList<String>();
        ArrayList<Integer> likes = new ArrayList<Integer>();
        ArrayList<String> likesStats = new ArrayList<String>();
        while (resultSet.next())
        {
            dates.add(resultSet.getString("Date"));
        }
        int i,j;
        for(i=0;i<dates.size();i++)
        {
            likes.add(1);
            for(j=i+1;j<dates.size();j++)
            {
                if(dates.get(i).equals(dates.get(j)))
                {
                    likes.set(likes.size()-1,likes.get(likes.size()-1)+1);
                    dates.remove(j);
                    j--;
                }
            }
        }
        for(i=0;i<dates.size();i++)
        {
            likesStats.add(dates.get(i)+" : "+likes.get(i)+" likes");
        }
        return likesStats;
    }
    public static void postView(int ContentID) throws SQLException
    {
        String Date = java.time.LocalDate.now().toString();
        String sql = "INSERT INTO postview (ContentID,Date) VALUES ('"+ContentID+"','"+Date+"')";
        statement.execute(sql);
    }
    public static ArrayList<String> getPostViews(int ContentID) throws SQLException
    {
        String sql = "SELECT Date FROM postview WHERE ContentID = '"+ContentID+"';";
        ResultSet resultSet = statement.executeQuery(sql);
        ArrayList<String> dates = new ArrayList<String>();
        ArrayList<Integer> views = new ArrayList<Integer>();
        ArrayList<String> postViews = new ArrayList<String>();
        while (resultSet.next())
        {
            dates.add(resultSet.getString("Date"));
        }
        int i,j;
        for(i=0;i<dates.size();i++)
        {
            views.add(1);
            for(j=i+1;j<dates.size();j++)
            {
                if(dates.get(i).equals(dates.get(j)))
                {
                    views.set(views.size()-1,views.get(views.size()-1)+1);
                    dates.remove(j);
                    j--;
                }
            }
        }
        for(i=0;i<dates.size();i++)
        {
            postViews.add(dates.get(i)+" : "+views.get(i));
        }
        return postViews;
    }
    public static ArrayList<Integer> getSuggestedPostIDs(int UserID) throws SQLException
    {
        ArrayList<Integer> followingsIDs = SQL.getFollowingsIDByID(UserID);
        ArrayList<Integer> postIDs = new ArrayList<Integer>();
        if(followingsIDs.size()!=0)
        {
            String sql = "SELECT ContentID FROM likes WHERE Date !='0' AND (";
            int i, j;
            for (i = 0; i < followingsIDs.size(); i++)
            {
                sql += "UserID = '" + followingsIDs.get(i) + "'";
                if (i != followingsIDs.size() - 1)
                {
                    sql += " OR ";
                }
            }
            sql += ");";
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next())
            {
                postIDs.add(resultSet.getInt("ContentID"));
            }
            for (i = 0; i < postIDs.size(); i++)
            {
                for (j = 0; j < followingsIDs.size(); j++)
                {
                    if (SQL.getUserIDByContentID(postIDs.get(i)) == followingsIDs.get(j) || SQL.getUserIDByContentID(postIDs.get(i)) == UserID)
                    {
                        postIDs.remove(i);
                        i--;
                        break;
                    }
                }
            }
            for (i = 0; i < postIDs.size(); i++)
            {
                for (j = i + 1; j < postIDs.size(); j++)
                {
                    if (postIDs.get(i) == postIDs.get(j))
                    {
                        postIDs.remove(j);
                        j--;
                    }
                }
            }
        }
        return postIDs;
    }
    public static int getUserIDByContentID(int ContentID) throws SQLException
    {
        String sql = "SELECT UserID FROM contents WHERE ID = '"+ContentID+"';";
        ResultSet resultSet = statement.executeQuery(sql);
        int UserID=0;
        while (resultSet.next())
        {
            UserID = resultSet.getInt("UserID");
        }
        return UserID;
    }
    public static void updateProfile(int ID,String profile) throws SQLException
    {
        String sql = "UPDATE users SET ProfileImage = '"+profile+"' WHERE ID = '"+ID+"';";
        statement.execute(sql);
    }
    public static boolean followed(int FollowerID,int FollowingID) throws SQLException
    {
        String sql = "SELECT *FROM relations WHERE FollowerID = '"+FollowerID+"' AND FollowingID = '"+FollowingID+"';";
        ResultSet resultSet = statement.executeQuery(sql);
        boolean followed = false;
        while (resultSet.next())
        {
            followed = true;
        }
        return followed;
    }
    public static ArrayList<Integer> getHomePagePostsIDByID(int UserID) throws SQLException
    {
        ArrayList<Integer> postsID1 = SQL.getFollowingsPostsIDByID(UserID);
        ArrayList<Integer> postsID2 = SQL.getSuggestedPostIDs(UserID);
        ArrayList<Integer> postsID = new ArrayList<Integer>();
        postsID.addAll(postsID1);
        postsID.addAll(postsID2);
        if(postsID.size()!=0)
        {
            String sql = "SELECT ID FROM contents WHERE ";
            int i;
            for (i = 0; i < postsID.size(); i++)
            {
                sql += "ID = '" + postsID.get(i) + "'";
                if (i < postsID.size() - 1)
                {
                    sql += " OR ";
                }
            }
            sql += ";";
            ArrayList<Integer> IDs = new ArrayList<Integer>();
            ResultSet resultSet = statement.executeQuery(sql);
            while (resultSet.next())
            {
                IDs.add(resultSet.getInt("ID"));
            }
            return IDs;
        }
        else
        {
            return postsID;
        }
    }
}
