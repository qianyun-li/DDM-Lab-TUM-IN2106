import findspark
findspark.init()

from pyspark import SparkContext

k = 6

embedding_kmers = []
with open("/home/ubuntu/kmers/random_300_kmers/randome_6mers") as f:
    embedding_kmers = [x.rstrip() for x in f.readlines()]

def embed(seq):
    result = ['1' if kmer in seq else '0' for kmer in embedding_kmers]
    return (seq[:6], ''.join(result))


if __name__ == '__main__':
    sequences_file = '/data/linearized_squence.fasta'
    #sequences_file = '/data/test_set'
    sc = SparkContext("spark://192.168.2.84:7077", "Kmer another search")
    sequences = sc.textFile(sequences_file).filter(lambda x: not x.startswith('>'))
    sequences_par = sequences.map(embed).reduceByKey(lambda a, b: a).saveAsTextFile("/spark_results/anotherk6")
    sc.stop()
