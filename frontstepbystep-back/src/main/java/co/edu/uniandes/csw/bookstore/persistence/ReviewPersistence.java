/*
The MIT License (MIT)
Copyright (c) 2015 Los Andes University
Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:
The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package co.edu.uniandes.csw.bookstore.persistence;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import co.edu.uniandes.csw.bookstore.entities.ReviewEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.persistence.TypedQuery;

/**
 * @generated
 */
@Stateless
public class ReviewPersistence {

    private static final Logger logger = Logger.getLogger(ReviewPersistence.class.getName());
 
    @PersistenceContext(unitName = "BookStorePU")
    protected EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    protected Class<ReviewEntity> getEntityClass() {
        return ReviewEntity.class;
    }

    public ReviewEntity create(ReviewEntity entity) {
        logger.info("Creando un premio nuevo");
        em.persist(entity);
        logger.info("Premio creado");
        return entity;
    }

    public ReviewEntity update(ReviewEntity entity) {
        logger.log(Level.INFO,"Actualizando premio con id={0}", entity.getId());
        return em.merge(entity);
    }

    public void delete(Long id) {
        logger.log(Level.INFO, "Borrando premio con id={0}", id);
        ReviewEntity entity = em.find(ReviewEntity.class, id);
        em.remove(entity);
    }

    public ReviewEntity find(Long bookid, Long reviewid) {
        TypedQuery<ReviewEntity> q = em.createQuery("select p from ReviewEntity p where (p.book.id = :bookid) and (p.id = :reviewid)", ReviewEntity.class);
        q.setParameter("bookid", bookid);
        q.setParameter("reviewid", reviewid);
        return q.getSingleResult();
    }

    public List<ReviewEntity> findAll(Integer page, Integer maxRecords, Long bookid) {
        TypedQuery<ReviewEntity> q = em.createQuery("select p from ReviewEntity p where (p.book.id = :bookid)", ReviewEntity.class);
        q.setParameter("bookid", bookid);
        if (page != null && maxRecords != null) {
            q.setFirstResult((page - 1) * maxRecords);
            q.setMaxResults(maxRecords);
        }
        return q.getResultList();
    }

}
