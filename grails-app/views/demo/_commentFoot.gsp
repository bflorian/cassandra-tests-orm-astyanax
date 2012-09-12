${comment.occurTime} -- ${comment.likedByCount()} likes
<g:remoteLink action="toggleCommentLike" id="${comment.id}" update="[success: 'like_'+comment.uuid]">
	<span>${comment.isLikedBy(person) ? "Unlike" : "Like"}</span>
</g:remoteLink>