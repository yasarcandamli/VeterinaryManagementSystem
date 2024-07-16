package dev.patika.VeterinaryManagementSystem.dao;


import dev.patika.VeterinaryManagementSystem.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AppointmentRepo extends JpaRepository<Appointment, Integer> {
}
