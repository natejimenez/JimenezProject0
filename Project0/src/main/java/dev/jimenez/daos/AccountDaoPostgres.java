package dev.jimenez.daos;

import dev.jimenez.entities.Account;
import dev.jimenez.utils.ConnectionUtil;

import java.sql.*;
import java.util.HashSet;
import java.util.Set;

public class AccountDaoPostgres implements AccountDAO{

    @Override
    public Account createAccount(int assignedClientId, Account account) {

        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "insert into account (assigned_client_id,account_type,balance) values (?,?,?)";

            PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            ps.setInt(1,account.getAssignedClientId());
            ps.setString(2,account.getAccountType());
            ps.setDouble(3,account.getBalance());

            ps.execute();

            ResultSet rs = ps.getGeneratedKeys();
            rs.next();
            int key = rs.getInt("account_id");
            account.setAccountId(key);
            return account;

        }catch(SQLException sqlException){
            sqlException.printStackTrace();
            return null;

        }
    }

    @Override
    public Set<Account> getAllAccountsByClientId(int id) {
        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "select * from account where assigned_client_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();
            Set<Account> accounts = new HashSet<Account>();

            while(rs.next()) {
                Account account = new Account();
                account.setClientId(rs.getInt("assigned_client_id"));
                account.setAccountType(rs.getString("account_type"));
                account.setAccountId(rs.getInt("account_id"));
                account.setBalance(rs.getDouble("balance"));
                accounts.add(account);
            }
            return accounts;

        }catch (SQLException sqlException){
            sqlException.printStackTrace();
            return null;

        }
    }

    @Override
    public Account getAccountById(int assignedClientId, int id) {

        try(Connection conn = ConnectionUtil.createConnection()){

            String sql = "select * from account where assigned_client_id = ? and account_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,assignedClientId);
            ps.setInt(2,id);
            ResultSet rs = ps.executeQuery();
            rs.next();

            Account account = new Account();
            account.setClientId(rs.getInt("assigned_client_id"));
            account.setAccountType(rs.getString("account_type"));
            account.setAccountId(rs.getInt("account_id"));
            account.setBalance(rs.getDouble("balance"));
            return account;

        }catch(SQLException sqlException){
            sqlException.printStackTrace();
            return null;
        }
    }

    @Override
    public Account updateAccount(int assignedClientId, Account account) {

        try(Connection conn = ConnectionUtil.createConnection()){


            String sql = "update account set assigned_client_id = ?,account_type = ?,balance = ? where assigned_client_id = ? and account_id = ?";


            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,account.getAssignedClientId());
            ps.setString(2,account.getAccountType());
            ps.setDouble(3,account.getBalance());
            ps.setInt(4,assignedClientId);
            ps.setInt(5,account.getAccountId());
            ps.executeUpdate();
            return account;

        }catch(SQLException sqlException){
            sqlException.printStackTrace();
            return account;
        }
    }

    @Override
    public boolean deleteAccountById(int assignedClientId, int id) {

        try(Connection conn = ConnectionUtil.createConnection()){
            String sql = "delete from account where assigned_client_id = ? and account_id = ?";
            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,assignedClientId);
            ps.setInt(2,id);
            ps.execute();
            return true;

        }catch(SQLException sqlException){
            sqlException.printStackTrace();
            return false;
        }

    }
}
