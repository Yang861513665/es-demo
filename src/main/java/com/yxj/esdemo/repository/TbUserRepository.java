package com.yxj.esdemo.repository;

import com.yxj.esdemo.entity.Result;
import com.yxj.esdemo.entity.Student;
import com.yxj.esdemo.entity.TbUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import javax.xml.crypto.Data;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * @author yangxj
 * @date 2019/7/17
 */
public interface TbUserRepository extends JpaRepository<TbUser, Integer> {
    @Query(value = "select * from tb_user where name like ?1% order by birthday asc",nativeQuery = true)
    List<TbUser> findByNameLike(String name);

    @Query(value = "select * from tb_user where birthday between ?1 and ?2",nativeQuery = true)
    List<TbUser> findByBirthday(Date start, Date end);
    @Query(value = "select convert (sum(money),char) as 'sum' ,name as 'name' from tb_user group  by name",nativeQuery = true)
    List<Map<String,String>> groupById();
    @Query(value = "select * from tb_user where id in ?1",nativeQuery = true)
    List<TbUser> findByIdIn(List<Integer> ids);
}
