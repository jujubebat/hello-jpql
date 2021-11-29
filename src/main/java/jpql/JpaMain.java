package jpql;

import java.awt.MenuBar;
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
            Team teamA = new Team();
            teamA.setName("팀A");
            em.persist(teamA);

            Team teamB = new Team();
            teamB.setName("팀B");
            em.persist(teamB);

            Member member1 = new Member();
            member1.setUsername("회원1");
            member1.setTeam(teamA);
            em.persist(member1);

            Member member2 = new Member();
            member2.setUsername("회원2");
            member2.setTeam(teamA);
            em.persist(member2);

            Member member3 = new Member();
            member3.setUsername("회원3");
            member3.setTeam(teamB);
            em.persist(member3);

            em.flush();
            em.clear();

            // 엔티티로 조회하기
            /*
            String query = "select m From Member m where m = :member";

            Member findMember = em.createQuery(query, Member.class)
                .setParameter("member", member1)
                .getSingleResult();
             */

            // 엔티티 프라이머리 키로 조회하기
            /*
            String query = "select m From Member m where m.id = :memberId";

            Member findMember = em.createQuery(query, Member.class)
                .setParameter("memberId", member1.getId())
                .getSingleResult();

             */

            // 외래 키 값으로 조회하기
            String query = "select m From Member m where m.team = :team";

            List<Member> members = em.createQuery(query, Member.class)
                .setParameter("team", teamA)
                .getResultList();

            for (Member member :  members){
                System.out.println("member = " + member);
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
