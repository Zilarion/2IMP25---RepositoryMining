# posts <- read.csv("/Users/ruudandriessen/study/2imp25/2IMP25-RepositoryMining/RepositoryMining/out/Posts_CPP_Python_Results.csv")
# source("/Users/ruudandriessen/study/2imp25/2IMP25-RepositoryMining/RepositoryMining/out/visualize.Rd")

# Python
posts.python <- posts[!grepl("python", posts$Tags),]
posts.python <- posts.python[posts.python$FavoriteCount != "null",]
posts.python.polite <- posts.python[posts.python$Polite > 0.8,]
posts.python.impolite <- posts.python[posts.python$Impolite > 0.8,]
# PDP python
par(mfrow=c(2, 5))
colnames <- c("Score", "FavoriteCount", "ViewCount", "AnswerCount", "CommentCount");
for (col in colnames) {
	d <- density(as.numeric(posts.python.polite[, col]))
   	plot(d, type="n", main=col)
    polygon(d, col="green", border="gray")
}

# PDP cpp
colnames <- c("Score", "FavoriteCount", "ViewCount", "AnswerCount", "CommentCount");
for (col in colnames) {
    d <- density(as.numeric(posts.python.impolite[, col]))
    plot(d, type="n", main=col)
    polygon(d, col="red", border="gray")
}

# CPP
posts.cpp <- posts[!grepl("c\\+\\+", posts$Tags),]
posts.cpp <- posts.cpp[posts.cpp$FavoriteCount != "null",]
posts.cpp.polite <- posts.cpp[posts.cpp$Polite > 0.8,]
posts.cpp.impolite <- posts.cpp[posts.cpp$Impolite > 0.8,]
# PDP python
colnames <- c("Score", "FavoriteCount", "ViewCount", "AnswerCount", "CommentCount");
for (col in colnames) {
	d <- density(as.numeric(posts.cpp.polite[, col]))
    plot(d, type="n", main=col)
    polygon(d, col="green", border="gray")
}

# PDP cpp
colnames <- c("Score", "FavoriteCount", "ViewCount", "AnswerCount", "CommentCount")
for (col in colnames) {
    d <- density(as.numeric(posts.cpp.impolite[, col]))
    plot(d, type="n", main=col)
    polygon(d, col="red", border="gray")
}
