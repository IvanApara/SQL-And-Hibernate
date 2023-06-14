package org.example;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        StandardServiceRegistry registry = new StandardServiceRegistryBuilder().
                configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(registry).getMetadataBuilder().build();
        SessionFactory sessionFactory = metadata.getSessionFactoryBuilder().build();

        Session session = sessionFactory.openSession();

        CriteriaBuilder builder = session.getCriteriaBuilder();
        CriteriaQuery<Course> query = builder.createQuery(Course.class);
        Root<Course> root = query.from(Course.class);
        query.select(root).where(builder.greaterThan(root.get("price"), 100_000)).
                orderBy(builder.desc(root.get("price")));;
        List<Course> courseList = session.createQuery(query).setMaxResults(5).getResultList();

        courseList.forEach(course -> System.out.println(course.getName() + " - " + course.getPrice()));

        sessionFactory.close();

//        String url = "jdbc:mysql://localhost:3306/skillbox";
//        String user = "root";
//        String pass = "Rjdfk`df666222";
//        try {
//            Connection connection = DriverManager.getConnection(url, user, pass);
//            Statement statement = connection.createStatement();
//            ResultSet resultSet = statement.executeQuery("SELECT pl.course_name,\n" +
//                    "COUNT(*) / 8 count\n" +
//                    "FROM PurchaseList pl\n" +
//                    "WHERE MONTH(pl.subscription_date) BETWEEN 1 AND 8\n" +
//                    "GROUP BY pl.course_name;");
//
//            while (resultSet.next()){
//                String courseName = resultSet.getString("course_name");
//                String count = resultSet.getString("count");
//                System.out.println(courseName.concat(" - ").concat(count));
//            }
//            resultSet.close();
//            statement.close();
//            connection.close();
//        } catch (Exception e){
//            e.printStackTrace();
//        }
    }
}