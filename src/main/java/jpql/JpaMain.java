package jpql;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.OrderColumn;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Team team = new Team();
            team.setName("teamA");
            em.persist(team);

            Member member = new Member();
            member.setUsername("관리자");
            member.setAge(10);

            member.setTeam(team);

            em.persist(member);

            em.flush();
            em.clear();

            // String query = "select 'a' || 'b' From Member m";
            // String query = "select concat('a', 'b') From Member m";
            // String query = "select substring(m.username, 2, 3) From Member m";
//            List<String> result = em.createQuery(query, String.class).getResultList();

//            for (String s : result) {
//                System.out.println("s = " + s);
//            }

            /*
            String query = "select size(t.members) From Team t";
            List<Object> result = em.createQuery(query, Object.class).getResultList();

            for (Object s : result) {
                System.out.println("s = " + s);
            }
            */

            String query = "select locate('de','abcdefg') From Member m";
            List<Integer> result = em.createQuery(query, Integer.class).getResultList();

            for (Integer s : result) {
                System.out.println("s = " + s);
            }

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

}
