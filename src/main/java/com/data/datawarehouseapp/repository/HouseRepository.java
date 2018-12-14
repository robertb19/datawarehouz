package com.data.datawarehouseapp.repository;
import com.data.datawarehouseapp.model.House;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HouseRepository extends JpaRepository<House,Long> {

}
