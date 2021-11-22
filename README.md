# ProyectoFinalPFSD
1.Ejecutar el productor TweetProducer.scala
2.Consumidor normal TweetConsumer.scala
3.Consumidor que genera backUp BackupConsumerTweets.scala, este genera un .json TweetsBackup.json
4.procesamiento lee TweetsBackup.json, mediante el objeto Procesamiento.scala y finalmente se guarda en un hdsf de hadoop

Como comenente en la clase intenten guardar el mètodo userRetweetsFollowers,para almacenar en la base de datos sql de la clase 
Procesamiento.scala utilizo  SaveUserRetweetsFollowersCommand(PostgresRepositoryTweets,data.toList).execute(), pero no me escribe 
los datos en la bd llega (), creeria que es de la serializacion del case class, intente crear el proho TweetMessageFollowers.proto pero
al parece no me compilaba y mucho menos construia.

En cuanto a aws no encontre la forma de conectarme por ssh, ni por putty, ni por mobaxterm.ni con ips publicas,le trabaje 2 dias y nada.