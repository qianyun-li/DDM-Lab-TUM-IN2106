import findspark
findspark.init()

from pyspark import SparkContext

k = 3
#sc = SparkContext("spark://192.168.2.84:7077", "Kmer search python")
#embedding_kmers = sc.textFile('~/kmers/random_300_kmers/randome_3mers')
embedding_kmers = []
with open("/home/ubuntu/kmers/random_300_kmers/randome_3mers") as f:
    embedding_kmers = f.readlines()
#no_embedding_kmers = embedding_kmers.count()
no_embedding_kmers = len(embedding_kmers)
def embed(seq):
    result = [1 if (embedding_kmers[i] in seq) else 0 for i in range(no_embedding_kmers) ]
    return (seq, result)


if __name__ == '__main__':
    sequences_file = '/data/linearized_squence.fasta'
    #sequences_file = '/home/ubuntu/kmers_python/test_seq.in'
    sc = SparkContext("spark://192.168.2.84:7077", "Kmer search python")
    sequences = sc.textFile(sequences_file).filter(lambda x: not x.startswith('>'))
    sequences_par = sequences.map(embed).reduceByKey(lambda a, b: a).sortByKey()
    sequences_par.map(lambda x: (x[1], x[0])).sortByKey(False).saveAsTextFile("/spark_results/search_for_3_kmers_embeddings.out")
    sc.stop()
