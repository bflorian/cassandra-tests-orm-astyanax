package demo

/**
 * @author: Bob Florian
 */
class DemoController 
{
	def index()
	{
		if (session.userId) {
			def person = currentUser
			def posts = person.traverseRelationships.friends.posts(max:3, reversed: true).execute(max: 50, reversed: true)
			render view: "/demo/feed", model: [person: person, posts: posts]
		}
		else {
			render view: "/demo/login"
		}
	}


	def signup()
	{

	}


	def logout()
	{
		session.userId = null
		redirect action: "index"
	}

	def addPost()
	{
		def person = currentUser
		person.addToPosts(new Post(text: params.text, occurTime:  new Date()))
		redirect action: "index"
	}

	def authenticate()
	{
		session.userId = Person.get(params.username)?.id
		redirect action: "index"
	}

	def createUser()
	{
		def person = new Person(username: params.username, firstName:  params.firstName, lastName:  params.lastName)
		person.save()
		session.userId = person.id
		redirect action: "index"
	}

	def addComment()
	{
		def post = Post.get(params.id)
		post.addToComments(new Comment(text: params.text, person: currentUser, occurTime: new Date())).save()
		redirect action: "index"
	}

	def togglePostLike()
	{
		def person = currentUser
		def post = Post.get(params.id)
		if (post.isLikedBy(person)) {
			post.removeFromLikedBy(person)
		}
		else {
			post.addToLikedBy(person)
		}
		render template: "postFoot", model: [person:person, commentCount: post.commentsCount(), post: post]
	}

	def toggleCommentLike()
	{
		def person = currentUser
		def comment = Comment.get(params.id)
		if (comment.isLikedBy(person)) {
			comment.removeFromLikedBy(person)
		}
		else {
			comment.addToLikedBy(person)
		}
		render template: "commentFoot", model: [person:person, comment: comment]
	}

	private Person getCurrentUser()
	{
		Person.get(session.userId)
	}
}
