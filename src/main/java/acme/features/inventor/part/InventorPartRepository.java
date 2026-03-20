
package acme.features.inventor.part;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.client.repositories.AbstractRepository;
import acme.entities.inventions.Invention;
import acme.entities.inventions.Part;

@Repository
public interface InventorPartRepository extends AbstractRepository {

	List<Part> findPartsByInventionId(int inventionId);

	@Query("select i from Part i where i.id = :id")
	Part findPartById(int id);

	@Query("select count(p) from Part p where p.invention.id = :inventionId")
	int countByInventionId(int inventionId);

	@Query("select i from Invention i where i.id = :inventionId")
	Invention findInventionById(int inventionId);

}
