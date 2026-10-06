package com.apexfit.backend.repository;

import com.apexfit.backend.entity.Progreso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ProgresoRepository extends JpaRepository<Progreso, Integer> {
    List<Progreso> findByUsuario_IdUsuarioOrderByFechaDesc(Integer idUsuario);
}
