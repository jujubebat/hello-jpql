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
            em.persist(member);

            em.flush();
            em.clear();

            // 아래와 같이 엔티티를 조회할 수 있다.(엔티티 조회) 엔티티 조회를 한 엔티티는 영속성 컨텍스트에서 관리된다!
            List<Member> result = em.createQuery("select m from Member m", Member.class).getResultList();

            // Team 엔티티를 가져오기 위해 join 쿼리가 나간다.
            // 참고로, 아래와 같이 작성하지 말고, join을 직접 사용해서 join이 된다는 것을 명시적으로 표햔하는게 좋다.
            // List<Team> result = em.createQuery("select m.team from Member m", Team.class).getResultList();

            Member findMember = result.get(0);
            findMember.setAge(20);

            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
    }

}
