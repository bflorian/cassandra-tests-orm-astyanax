package example

import org.junit.Test
import static org.junit.Assert.*

/**
 * @author: Bob Florian
 */
class ExampleTests 
{
	@Test
	void testSetup()
	{
		for (p in 1..3) {
			def person = new Person(username: "user$p").save()

			for (q in 1..2) {
				person.addToPosts(new Post(title: "User $p's Post $q", text: "Text of post q", occurTime: new Date()))
				println "User $p's Post $q"
			}
			/*
			person.posts.eachWithIndex {post, i ->
				for (k in 1..3) {
					post.addToComments(new Comment(person: person, text:  "Comment $k to user $p's post $i", occurTime:  new Date()))
				}
			}
			*/
		}

		Person.list().eachWithIndex {p1, i1 ->
			Person.list().eachWithIndex {p2, i2 ->
				if (p1.username != p2.username /* && (i1 % 2) == (i2 % 2) */) {
					p1.addToFriends(p2)
				}
			}
		}

		Post.list().eachWithIndex {post, i ->
			Person.list().eachWithIndex {person, j ->
				if (post.person.username != person.username) {
					//if (post.person.isFriendOf(person)) {
						//if ((i % 2) == (j % 2)) {
					        println "${post.title} liked by ${person.username}"
							post.addToLikedBy(person)
						//}
					//}
				}
			}
		}
	}

	@Test
	void testTraverseAll()
	{
		def feed1 = Person.get("user1").traverseRelationships.posts.comments.execute()
		println feed1
	}

	@Test
	void testTraverse32()
	{
		def feed1 = Person.get("user1").traverseRelationships.posts(max:3, reversed: true).comments(max: 2, reversed: true).execute()
		println feed1
	}

	@Test
	void testFeed1()
	{
		def feed1 = Person.get("user1").traverseRelationships.friends.posts(max:2, reversed: true).execute(max: 5, reversed:  true)
		feed1.each {
			println it.title
		}
	}

}
