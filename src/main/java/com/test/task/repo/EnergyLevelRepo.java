package com.test.task.repo;

import com.test.task.model.EnergyLevel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EnergyLevelRepo extends JpaRepository<EnergyLevel, Long> {

    EnergyLevel findEnergyLevelByIsin(String isin);
}
