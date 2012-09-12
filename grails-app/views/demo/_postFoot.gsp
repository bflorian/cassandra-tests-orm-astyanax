${post.occurTime} -- ${commentCount} comments -- ${post.likedByCount()} likes
<g:remoteLink action="togglePostLike" id="${post.id}" update="[success: 'like_'+post.id]">
	<span>${post.isLikedBy(person) ? "Unlike" : "Like"}</span>
</g:remoteLink>
