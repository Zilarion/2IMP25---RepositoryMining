package repositorymining;

/**
 *
 * @author ruudandriessen
 */
public class Post {
    int id, postTypeId, acceptedAnswerId, ownerUserId, lastEditorUserId;
    String creationDate, lastEditDate, lastActivityDate, closedDate, communityOwnedDate;
    int score, viewCount, answerCount, commentCount, favoriteCount;
    String title, body, lastEditorDisplayName, ownerDisplayName;
}
