import React, { useState } from 'react';
import {
    Button,
    Modal,
    ModalOverlay,
    ModalContent,
    ModalHeader,
    ModalFooter,
    ModalBody,
    ModalCloseButton,
    FormControl,
    FormLabel,
    Input,
    Stack,
    useDisclosure,
    Box, Tag, Heading, Text
} from '@chakra-ui/react';
import { SearchIcon } from '@chakra-ui/icons';
import { searchCustomersByParameter } from './services/client.js';

const SearchFormButton = () => {
    const { isOpen, onOpen, onClose } = useDisclosure();
    const [step, setStep] = useState('select');
    const [searchParameter, setSearchParameter] = useState('');
    const [formData, setFormData] = useState('');
    const [searchResults, setSearchResults] = useState([]);
    const [error, setError] = useState('');

    const handleParameterClick = (parameter) => {
        setSearchParameter(parameter);
        setStep('input');
    };

    const handleChange = (e) => {
        setFormData(e.target.value);
    };

    const handleSubmit = async () => {
        setError('');
        try {
            console.log("Searching for", searchParameter, "with query", formData);
            const results = await searchCustomersByParameter(searchParameter, formData);
            console.log("Search results:", results);
            setSearchResults(results);
            resetForm();
            onClose();
        } catch (e) {
            setError('Search failed. Please try again.');
            console.error("Search error:", e);
        }
    };

    const resetForm = () => {
        setSearchParameter('');
        setFormData('');
        setStep('select');
        setSearchResults([]);
    };

    return (
        <>
            <Button
                rightIcon={<SearchIcon />}
                colorScheme="blue"
                onClick={() => {
                    resetForm();
                    onOpen();
                }}
                top="3"
                left="67%"
            >
                Search
            </Button>

            <Modal isOpen={isOpen} onClose={onClose}>
                <ModalOverlay />
                <ModalContent bg="gray.300" color="black">
                    <ModalHeader>{step === 'select' ? 'Select a search parameter' : `Search by ${searchParameter}`}</ModalHeader>
                    <ModalCloseButton />
                    <ModalBody>
                        {step === 'select' ? (
                            <Stack spacing={5} maxW={350} marginX={6}>
                                <Button onClick={() => handleParameterClick('name')} colorScheme="blue">
                                    Search by Name
                                </Button>
                                <Button onClick={() => handleParameterClick('age')} colorScheme="blue">
                                    Search by Age
                                </Button>
                                <Button onClick={() => handleParameterClick('email')} colorScheme="blue">
                                    Search by Email
                                </Button>
                            </Stack>
                        ) : (
                            <Stack spacing={4} marginTop={6}>
                                <FormControl id={searchParameter}>
                                    <FormLabel>
                                        {searchParameter.charAt(0).toUpperCase() + searchParameter.slice(1)}:
                                    </FormLabel>
                                    <Input
                                        name={searchParameter}
                                        value={formData}
                                        onChange={handleChange}
                                        placeholder={`Enter ${searchParameter}`}
                                    />
                                </FormControl>
                            </Stack>
                        )}
                    </ModalBody>

                    <ModalFooter>
                        {step === 'select' ? (
                            <Button isDisabled></Button>
                        ) : (
                            <>
                                <Button colorScheme="blue" mr={3} onClick={handleSubmit}>
                                    Search
                                </Button>
                                <Button variant="ghost" onClick={resetForm}>
                                    Back to Selection
                                </Button>
                                <Button variant="ghost" onClick={onClose}>
                                    Cancel
                                </Button>
                            </>
                        )}
                    </ModalFooter>
                </ModalContent>
            </Modal>

            <Box p={4}>
                {error && <p>{error}</p>}
                {searchResults.length > 0 && (
                    <Stack spacing={3} mt={4}>
                        {searchResults.map((customer) => (
                            <Box key={customer.id} p={3} borderWidth={1} borderRadius="md">
                                <h3>Name: {customer.name}</h3>
                                <p>Email: {customer.email}</p>
                                <p>Age: {customer.age}</p>
                                <p>Age: {customer.gender}</p>
                            </Box>
                        ))}
                    </Stack>

                )}
            </Box>
        </>
    );
};

export default SearchFormButton;
