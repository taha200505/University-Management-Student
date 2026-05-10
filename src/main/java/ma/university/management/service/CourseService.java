package ma.university.management.service;

import ma.university.management.dto.CourseDTO;

import java.util.List;

public interface CourseService {

    List<CourseDTO> getAllCourses();

    CourseDTO getCourseById(Long id);

    CourseDTO createCourse(CourseDTO dto);

    CourseDTO updateCourse(Long id, CourseDTO dto);

    void deleteCourse(Long id);

    List<CourseDTO> getCoursesByFiliere(Long filiereId);
}
