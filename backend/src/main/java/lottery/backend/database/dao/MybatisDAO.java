package lottery.backend.database.dao;

import com.talanlabs.avatargenerator.Avatar;
import com.talanlabs.avatargenerator.GitHubAvatar;
import lottery.backend.CommonUtil;
import lottery.backend.database.model.UserEntity;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.ibatis.session.SqlSession;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Collections;

import lottery.backend.database.model.UserTuple;

import javax.imageio.ImageIO;

public class MybatisDAO {
    final static int width = 50, height = 50;
    Avatar avatar = GitHubAvatar.newAvatarBuilder().size(width, height).build();
    // 这里直接使用 bean 注入
    private SqlSession sqlSession;
    public void setSqlSession(SqlSession sqlSession) {
        this.sqlSession = sqlSession;
    }

    public List<String> selectEnrolledUsers() {
        String statement = "lottery.backend.database.UserMapper.selectEnrolledUsers";
        List<UserEntity> listUser = sqlSession.selectList(statement);
        List<String> result = new ArrayList<>();
        for (UserEntity user : listUser) {
            result.add(user.getName());
        }
        return result;
    }
    public boolean checkUserExists(String jAccount) {
        String statement = "lottery.backend.database.UserMapper.jAccountExists";
        return sqlSession.selectOne(statement, jAccount);
    }
    public boolean registerUser(String jAccount, String name) {
        if (checkUserExists(jAccount)) {
            System.out.println(String.format("用户 %s 已经存在，不再创建新用户", jAccount));
            return true;
        }
        String statement = "lottery.backend.database.UserMapper.insertUser";
//        byte[] imageBytes = avatar.createAsPngBytes(123456L);
        BufferedImage bufferedImage = CommonUtil.generateAvatar(name);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        try {
            ImageIO.write(bufferedImage, "png", baos);
        } catch (Exception e) {
            System.err.println("Error writing image: " + e.getMessage());
            e.printStackTrace();
        }
        Byte[] imageBytes = ArrayUtils.toObject(baos.toByteArray());
        UserEntity user = new UserEntity(jAccount, name, null, false, imageBytes);
        int result = sqlSession.insert(statement, user);
        System.out.println(String.format("用户 %s %s", name, (result > 0) ? "添加成功！" : "添加失败！"));
        return result > 0;
    }
    public boolean setEnrolledTrue(String jAccount) {
        String statement = "lottery.backend.database.UserMapper.setEnrolledTrue";
        sqlSession.update(statement, jAccount);
        return true;
    }
    public List<UserTuple> setAndGetPrizeWinners(String prizeName, int num) {
        String selectStatement = "lottery.backend.database.UserMapper.selectNullPrizeUsers";
        List<UserEntity> allUsers = sqlSession.selectList(selectStatement);

        Collections.shuffle(allUsers);
        List<UserEntity> winners = allUsers.subList(0, Math.min(num, allUsers.size()));

        String updateStatement = "lottery.backend.database.UserMapper.updateUserPrize";
        for (UserEntity winner : winners) {
            winner.setPrize(prizeName);
            sqlSession.update(updateStatement, winner);
        }

        List<UserTuple> result = new ArrayList<>();
        for (UserEntity winner : winners) {
            result.add(new UserTuple(winner.getName(), winner.getProfile()));
        }
        return result;
    }
    public boolean uploadProfile(String jAccount, Byte[] profile) {
        String statement = "lottery.backend.database.UserMapper.updateUserProfile";
        sqlSession.update(statement, new UserEntity(jAccount, null, null, false, profile));
        return true;
    }
    public Byte[] getUserProfile(String jAccount) {
        String statement = "lottery.backend.database.UserMapper.selectUserProfile";
        return sqlSession.selectOne(statement, jAccount);
    }
}