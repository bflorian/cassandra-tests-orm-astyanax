package example

import org.junit.Test
import static org.junit.Assert.*

/**
 * @author: Bob Florian
 */
class ExampleTests 
{
	def astyanaxService

	@Test
	void testSetup1()
	{
		def numPersons = 3 //2
		def postsPerPerson = 2 //1
		def commentsPerPost = [2] //[1]
		def likesPerPost = [0,1,2] //[0,1]
		def likesPerComment = [0,1,2] //[0,1]

		// persons
		for (p in 1..numPersons) {
			def person = new Person(username: "user$p").save()

		}

		// friendships
		Person.list().eachWithIndex {p1, i1 ->
			Person.list().eachWithIndex {p2, i2 ->
				//if (p1.username != p2.username /* && (i1 % 2) == (i2 % 2) */) {
					p1.addToFriends(p2).addToFriends(p1)
				//}
			}
		}

		// posts
		Person.list().eachWithIndex {person, pi ->
			for (q in 1..postsPerPerson) {
				person.addToPosts(new Post(text: "User ${pi+1}'s Post $q", occurTime: new Date()))
				println "User ${pi+1}'s Post $q"
			}
		}

		// comments
		List persons = Person.list()
		Post.list().eachWithIndex {post, pi ->
			def cppi = commentsPerPost.size() == 1 ? 0 : rand.nextInt(commentsPerPost.size())
			def numComments = commentsPerPost[cppi]
			for (i in 1..numComments) {
				def ui = rand.nextInt(persons.size())
				def person = persons[ui]
				post.addToComments(new Comment(text: "Comments to post ${pi+1} from user ${ui+1}", occurTime: new Date(), person: person))
			}
		}

		// post likes
		Post.list().eachWithIndex {post, pi ->
			def cppi = likesPerPost.size() == 1 ? 0 : rand.nextInt(likesPerPost.size())
			def numLikes = likesPerPost[cppi]
			for (i in 1..numLikes) {
				def ui = rand.nextInt(persons.size())
				def person = persons[ui]
				post.addToLikedBy(person)
			}
		}

		// comment likes
		Comment.list().eachWithIndex {comment, pi ->
			def cppi = likesPerComment.size() == 1 ? 0 : rand.nextInt(likesPerComment.size())
			def numLikes = likesPerComment[cppi]
			for (i in 1..numLikes) {
				def ui = rand.nextInt(persons.size())
				def person = persons[ui]
				comment.addToLikedBy(person)
			}
		}

		astyanaxService.showColumnFamilies(["Person","Person_IDX","Post","Post_IDX","Post_CTR","Comment","Comment_IDX","Comment_CTR"], "example","standard",100,100)
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
			println it.text
		}
	}

	@Test
	void testCascadeDelete()
	{
		def u = Person.get("user1")
		assertEquals 3, Person.list().size()
		assertEquals 6, Post.list().size()
		assertEquals 12, Comment.list().size()
		u.delete(cascade: true)
		assertEquals 2, Person.list().size()
		assertEquals 4, Post.list().size()
		assertEquals 8, Comment.list().size()
	}

	def rand = new Random()

}
