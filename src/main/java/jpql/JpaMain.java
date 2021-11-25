package jpql;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

public class JpaMain {

    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("hello");
        EntityManager em = emf.createEntityManager();
        EntityTransaction tx = em.getTransaction();
        tx.begin();

        try {
            Member member = new Member();
            member.setUsername("member1");
            member.setAge(10);
            em.persist(member);

            em.flush();
            em.clear();

            // 1. Qeury 타입으로 조회
//            List resultList = em.createQuery("select distinct m.username, m.age from Member m").getResultList();
//            Object o = resultList.get(0);
//            Object[] result = (Object[]) o;

            // 2. Object[] 타입으로 조회
//            List<Object[]> resultList = em.createQuery("select distinct m.username, m.age from Member m").getResultList();
//            Object[] result = resultList.get(0);

            // 3. new 명령어로 조회
            List<MemberDTO> resultList = em.createQuery("select new jpql.MemberDTO(m.username, m.age) from Member m", MemberDTO.class)
                .getResultList();
            MemberDTO memberDTO = resultList.get(0);

            System.out.println("username = " + memberDTO.getUsername());
            System.out.println("age = " + memberDTO.getAge());

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

}
